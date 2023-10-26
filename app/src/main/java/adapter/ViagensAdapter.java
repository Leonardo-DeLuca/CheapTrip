package adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cheaptrip.R;

import java.text.DecimalFormat;
import java.util.List;

import database.dao.ViagemDAO;
import database.model.ViagemModel;

public class ViagensAdapter extends BaseAdapter {
    private Activity activity;
    private List<ViagemModel> listaViagens;
    private DecimalFormat df;

    public ViagensAdapter(Activity activity, List<ViagemModel> listaViagens) {
        this.activity = activity;
        this.listaViagens = listaViagens;
        this.df = new DecimalFormat("0.00");
    }

    @Override
    public int getCount() {
        return listaViagens.size();
    }

    @Override
    public Object getItem(int i) {
        return listaViagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaViagens.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.item_lista_viagem, viewGroup, false);
        }

        String titulo = listaViagens.get(i).getTitulo();
        String total = "R$ " + df.format(listaViagens.get(i).getTotal());
        String numeroViajantes = listaViagens.get(i).getTotalViajantes() + " " + activity.getString(R.string.viajantes);
        String duracao = listaViagens.get(i).getDuracao() + " " + activity.getString(R.string.dias);
        String data = listaViagens.get(i).getDataCriacao();

        TextView textTitulo = view.findViewById(R.id.textTituloViagem);
        textTitulo.setText(titulo);

        TextView textTotal = view.findViewById(R.id.textTotalViagem);
        textTotal.setText(total);

        TextView textNumeroViajantes = view.findViewById(R.id.textNumeroViajantesViagem);
        textNumeroViajantes.setText(numeroViajantes);

        TextView textDuracao = view.findViewById(R.id.textDuracaoViagem);
        textDuracao.setText(duracao);

        TextView textCriacao = view.findViewById(R.id.textDataCriacaoViagem);
        textCriacao.setText(data);

        return view;
    }

}
