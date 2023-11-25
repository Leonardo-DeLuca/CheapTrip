package activities.fragments.viagens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cheaptrip.R;
import com.example.cheaptrip.databinding.FragmentViagensBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

import activities.CadastroViagensActivity;
import activities.LoginActivity;
import activities.MainActivity;
import activities.ResumoViagemActivity;
import adapter.ViagensAdapter;
import api.Api;
import api.Conversor;
import api.model.DTOEnviar;
import api.model.Resposta;
import database.dao.EntretenimentoDAO;
import database.dao.GasolinaDAO;
import database.dao.HospedagemDAO;
import database.dao.RefeicoesDAO;
import database.dao.TarifaAereaDAO;
import database.dao.ViagemDAO;
import database.model.EntretenimentoModel;
import database.model.GasolinaModel;
import database.model.HospedagemModel;
import database.model.RefeicoesModel;
import database.model.TarifaAereaModel;
import database.model.ViagemModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.KeysUtil;

public class ViagensFragment extends Fragment {

    private FragmentViagensBinding binding;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnSync;
    private ListView listaViagens;
    private ViagensAdapter adapter;
    private ViagemDAO viagemDAO;
    private EntretenimentoDAO entretenimentoDAO;
    private GasolinaDAO gasolinaDAO;
    private HospedagemDAO hospedagemDAO;
    private RefeicoesDAO refeicoesDAO;
    private TarifaAereaDAO tarifaAereaDAO;
    private List<ViagemModel> viagensUsuario;
    private SharedPreferences preferences;
    private int idUsuario;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViagensBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdd = view.findViewById(R.id.btn_add_viagem);
        btnAdd.setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), CadastroViagensActivity.class);
            startActivityForResult(i, 1);
        });

        btnSync = view.findViewById(R.id.btn_sync);
        btnSync.setOnClickListener(view2 -> {
            sincronizarViagensAPI();
        });

        listaViagens = view.findViewById(R.id.lista_viagens);
        listaViagens.setOnItemClickListener((adapterView, view12, i, idViagem) -> {
            Intent it = new Intent(getActivity(), ResumoViagemActivity.class);
            it.putExtra("id_viagem", String.valueOf(idViagem));

            startActivityForResult(it, 1);
        });

        setaVariaveisExtras();
        recuperarViagensUsuario();
    }

    private void setaVariaveisExtras() {
        viagemDAO = new ViagemDAO(getContext());
        entretenimentoDAO = new EntretenimentoDAO(getContext());
        gasolinaDAO = new GasolinaDAO(getContext());
        hospedagemDAO = new HospedagemDAO(getContext());
        refeicoesDAO = new RefeicoesDAO(getContext());
        tarifaAereaDAO = new TarifaAereaDAO(getContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        idUsuario = preferences.getInt(KeysUtil.ID_USER_LOGIN, -1);
    }

    private void recuperarViagensUsuario() {
        viagensUsuario = viagemDAO.selectBy(ViagemModel.COLUNA_ID_USUARIO, String.valueOf(idUsuario));

        adapter = new ViagensAdapter(getActivity(), viagensUsuario);
        listaViagens.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void sincronizarViagensAPI() {
        List<ViagemModel> viagensNaoSincronizadas = viagensUsuario.stream().filter(v -> v.getSincronizada().equals("N")).collect(Collectors.toList());
        int quantidade = viagensNaoSincronizadas.size();

        if (quantidade == 0) {
            Toast.makeText(getContext(), "Sem viagens para sincronizar.", Toast.LENGTH_SHORT).show();
            return;
        }

        viagensNaoSincronizadas.forEach(viagem -> {
            DTOEnviar dtoEnviar = new DTOEnviar(viagem.getTotalViajantes(), viagem.getDuracao(), viagem.getTotal(), viagem.getTitulo());

            dtoEnviar.setViagemCustoEntretenimentos(Conversor.converterEntretenimentos(entretenimentoDAO.selectBy(EntretenimentoModel.COLUNA_ID_VIAGEM, String.valueOf(viagem.getId()))));
            dtoEnviar.setViagemCustoGasolina(Conversor.converterGasolina(gasolinaDAO.selectBy(GasolinaModel.COLUNA_ID_VIAGEM, String.valueOf(viagem.getId()))));
            dtoEnviar.setViagemCustoHospedagem(Conversor.converterHospedagem(hospedagemDAO.selectBy(HospedagemModel.COLUNA_ID_VIAGEM, String.valueOf(viagem.getId()))));
            dtoEnviar.setViagemCustoRefeicao(Conversor.converterRefeicao(refeicoesDAO.selectBy(RefeicoesModel.COLUNA_ID_VIAGEM, String.valueOf(viagem.getId()))));
            dtoEnviar.setViagemCustoAereo(Conversor.converterTarifaAerea(tarifaAereaDAO.selectBy(TarifaAereaModel.COLUNA_ID_VIAGEM, String.valueOf(viagem.getId()))));

            Api.postViagem(dtoEnviar, new Callback<Resposta>() {
                @Override
                public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                    if (response.isSuccessful()) {
                        viagem.setSincronizada("S");
                        viagemDAO.update(viagem);

                        Log.d("Debug - Mensagem Sinc.", response.body().getMensagem());
                        Log.d("Debug - Dados Sinc.", String.valueOf(response.body().getDados()));
                    }
                }

                @Override
                public void onFailure(Call<Resposta> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });

        Toast.makeText(getContext(), "Sincronização concluída.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && requestCode == 1) {
            recuperarViagensUsuario();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}