package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.cheaptrip.R;

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

public class ResumoViagemActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EntretenimentoDAO entretenimentoDAO;
    private GasolinaDAO gasolinaDAO;
    private HospedagemDAO hospedagemDAO;
    private RefeicoesDAO refeicoesDAO;
    private TarifaAereaDAO tarifaAereaDAO;
    private ViagemDAO viagemDAO;
    private ViagemModel viagemModel;
    private GasolinaModel gasolinaModel;
    private RefeicoesModel refeicoesModel;
    private HospedagemModel hospedagemModel;
    private TarifaAereaModel tarifaAereaModel;
    private EntretenimentoModel entretenimentoModel;

    private ImageButton arrow1, arrow2, arrow3, arrow4, arrow5;
    private LinearLayout hiddenView1, hiddenView2, hiddenView3, hiddenView4, hiddenView5;
    private CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    private TextView txtTotalKms, txtMediaKmsLitro, txtCustoMedioLitro, txtTotalVeiculos;

    private LinearLayout[] hiddenViews;
    private CardView[] cardViews;
    private ImageButton[] imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_viagem);

        setarIds();
        setaVariaveisExtras();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String idViagem = intent.getStringExtra("id_viagem");

        setModels(idViagem);

        hiddenViews = new LinearLayout[]{hiddenView1, hiddenView2, hiddenView3, hiddenView4, hiddenView5};
        cardViews = new CardView[]{cardView1, cardView2, cardView3, cardView4, cardView5};
        imageButtons = new ImageButton[]{arrow1, arrow2, arrow3, arrow4, arrow5};

        for (int i = 0; i < hiddenViews.length; i++){
            if(i == 0) {
                if(gasolinaModel.getAdicionouViagem() != 0) {
                    expandeCardView(imageButtons[i], hiddenViews[i], cardViews[i]);
                }
                else{
                    imageButtons[i].setAlpha(0.3F);
                    imageButtons[i].setOnClickListener(view ->{
                        Toast.makeText(ResumoViagemActivity.this, R.string.gasolinaAviso, Toast.LENGTH_SHORT).show();
                    });
                }
            }
            else if(i == 1) {
                if(tarifaAereaModel.getAdicionouViagem() != 0) {
                    expandeCardView(imageButtons[i], hiddenViews[i], cardViews[i]);
                }
                else{
                    imageButtons[i].setAlpha(0.3F);
                    imageButtons[i].setOnClickListener(view ->{
                        Toast.makeText(ResumoViagemActivity.this, R.string.tarifaAereaAviso, Toast.LENGTH_SHORT).show();
                    });
                }
            }

            else if(i == 2) {
                if(refeicoesModel.getAdicionouViagem() != 0) {
                    expandeCardView(imageButtons[i], hiddenViews[i], cardViews[i]);
                }
                else{
                    imageButtons[i].setAlpha(0.3F);
                    imageButtons[i].setOnClickListener(view ->{
                        Toast.makeText(ResumoViagemActivity.this, R.string.refeicoesAviso, Toast.LENGTH_SHORT).show();
                    });
                }
            }

            else if(i == 3) {
                if(hospedagemModel.getAdicionouViagem() != 0) {
                    expandeCardView(imageButtons[i], hiddenViews[i], cardViews[i]);
                }
                else{
                    imageButtons[i].setAlpha(0.3F);
                    imageButtons[i].setOnClickListener(view ->{
                        Toast.makeText(ResumoViagemActivity.this, R.string.hospedagemAviso, Toast.LENGTH_SHORT).show();
                    });
                }
            }

            else if(i == 4) {
                if(entretenimentoModel.getAdicionouViagem() != 0) {
                    expandeCardView(imageButtons[i], hiddenViews[i], cardViews[i]);
                }
                else{
                    imageButtons[i].setAlpha(0.3F);
                    imageButtons[i].setOnClickListener(view ->{
                        Toast.makeText(ResumoViagemActivity.this, R.string.entreterimentoAviso, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        }

        setDadosGasolina();
    }

    private void expandeCardView(ImageButton arrow, LinearLayout hiddenView, CardView cardView){
        arrow.setOnClickListener(view -> {
            if (hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_arrow_drop_down);
            }
            else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.ic_arrow_drop_up);
            }
        });
    }


    private  void setarIds(){
        toolbar = findViewById(R.id.toolbarResumoViagem);
        cardView1 = findViewById(R.id.base_cardview1);
        cardView2 = findViewById(R.id.base_cardview2);
        cardView3 = findViewById(R.id.base_cardview3);
        cardView4 = findViewById(R.id.base_cardview4);
        cardView5 = findViewById(R.id.base_cardview5);
        arrow1 = findViewById(R.id.arrow_button1);
        arrow2 = findViewById(R.id.arrow_button2);
        arrow3 = findViewById(R.id.arrow_button3);
        arrow4 = findViewById(R.id.arrow_button4);
        arrow5 = findViewById(R.id.arrow_button5);
        hiddenView1 = findViewById(R.id.hidden_view1);
        hiddenView2 = findViewById(R.id.hidden_view2);
        hiddenView3 = findViewById(R.id.hidden_view3);
        hiddenView4 = findViewById(R.id.hidden_view4);
        hiddenView5 = findViewById(R.id.hidden_view5);
        txtTotalKms = findViewById(R.id.resumo_totalKms);
        txtMediaKmsLitro = findViewById(R.id.resumo_mediaKmsLitro);
        txtCustoMedioLitro = findViewById(R.id.resumo_custoMedioLitro);
        txtTotalVeiculos = findViewById(R.id.resumo_totalVeiculos);
    }

    private void setaVariaveisExtras() {
        Context context = ResumoViagemActivity.this;
        gasolinaModel = new GasolinaModel();
        tarifaAereaModel = new TarifaAereaModel();
        refeicoesModel = new RefeicoesModel();
        hospedagemModel = new HospedagemModel();
        entretenimentoModel = new EntretenimentoModel();

        viagemDAO = new ViagemDAO(context);
        gasolinaDAO = new GasolinaDAO(context);
        tarifaAereaDAO = new TarifaAereaDAO(context);
        refeicoesDAO = new RefeicoesDAO(context);
        hospedagemDAO = new HospedagemDAO(context);
        entretenimentoDAO = new EntretenimentoDAO(context);
    }

    private void setModels(String idViagem){
        viagemModel = viagemDAO.selectById(String.valueOf(idViagem));
        gasolinaModel = gasolinaDAO.selectBy(GasolinaModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
        tarifaAereaModel = tarifaAereaDAO.selectBy(TarifaAereaModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
        refeicoesModel = refeicoesDAO.selectBy(RefeicoesModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
        hospedagemModel = hospedagemDAO.selectBy(HospedagemModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
        entretenimentoModel = entretenimentoDAO.selectBy(EntretenimentoModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
    }

    private void setDadosGasolina(){
        txtTotalKms.setText(String.valueOf(gasolinaModel.getTotalEstimadoKms()));
        txtMediaKmsLitro.setText(String.valueOf(gasolinaModel.getCustoMedioLitro()));
        txtCustoMedioLitro.setText(String.valueOf(gasolinaModel.getCustoMedioLitro()));
        txtTotalVeiculos.setText(String.valueOf(gasolinaModel.getTotalVeiculos()));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
