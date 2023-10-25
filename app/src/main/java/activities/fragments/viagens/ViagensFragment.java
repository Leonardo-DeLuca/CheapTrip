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

import activities.CadastroViagensActivity;
import activities.MainActivity;
import adapter.ViagensAdapter;
import database.dao.ViagemDAO;
import database.model.ViagemModel;
import util.KeysUtil;

public class ViagensFragment extends Fragment {

    private FragmentViagensBinding binding;
    private FloatingActionButton btnAdd;
    private ListView listaViagens;
    private ViagensAdapter adapter;
    private ViagemDAO viagemDAO;
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

        listaViagens = view.findViewById(R.id.lista_viagens);
        listaViagens.setOnItemClickListener((adapterView, view12, i, idViagem) -> {
            Toast.makeText(getContext(), "ID Viagem: " + idViagem, Toast.LENGTH_SHORT).show();
        });

        setaVariaveisExtras();
        recuperarViagensUsuario();
    }

    private void setaVariaveisExtras() {
        viagemDAO = new ViagemDAO(getContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        idUsuario = preferences.getInt(KeysUtil.ID_USER_LOGIN, -1);
    }

    private void recuperarViagensUsuario() {
        viagensUsuario = viagemDAO.selectBy(ViagemModel.COLUNA_ID_USUARIO, String.valueOf(idUsuario));

        adapter = new ViagensAdapter(getActivity(), viagensUsuario, viagemDAO);
        listaViagens.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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