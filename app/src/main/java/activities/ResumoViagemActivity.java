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

import java.text.DecimalFormat;
import java.util.HashMap;

import adapter.ViagensAdapter;
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

    private ImageButton arrow1, arrow2, arrow3, arrow4, arrow5, editBtn, deleteBtn;
    private LinearLayout hiddenView1, hiddenView2, hiddenView3, hiddenView4, hiddenView5, resumoLinearEnt1, resumoLinearEnt2, resumoLinearEnt3;
    private CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    private TextView txtTituloViagem, txtNumeroViajantesViagem, txtDuracaoViagem, txtDataCriacaoViagem, txtValorPorPessoa, txtTotalViagem,
            txtTotalKms, txtMediaKmsLitro, txtCustoMedioLitro, txtTotalVeiculos,
            txtCustoPorPessoa, txtAluguelVeiculo,
            txtCustoRefeicao, txtRefeicoesDia,
            txtCustoMedioNoite, txtTotalNoites, txtTotalQuartos,
            txtEntreterimento1, txtEntreterimento2, txtEntreterimento3, txtValorEntreterimento1, txtValorEntreterimento2, txtValorEntreterimento3,
            txtTotalFinalGasolina, txtTotalFinalTarifasAereas, txtTotalFinalHospedagem, txtTotalFinalRefeicoes, txtTotalFinalEntreterimento;

    private LinearLayout[] hiddenViews;
    private CardView[] cardViews;
    private ImageButton[] imageButtons;

    private HashMap<Integer, Object> map;

    private String idViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_viagem);

        setarIds();
        setaVariaveisExtras();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        idViagem = intent.getStringExtra("id_viagem");
        setModels(idViagem);
        setDadosViagem();

        hiddenViews = new LinearLayout[]{hiddenView1, hiddenView2, hiddenView3, hiddenView4, hiddenView5};
        cardViews = new CardView[]{cardView1, cardView2, cardView3, cardView4, cardView5};
        imageButtons = new ImageButton[]{arrow1, arrow2, arrow3, arrow4, arrow5};

        for (int i = 0; i < hiddenViews.length; i++){
            defineFuncaoArrow(imageButtons[i], hiddenViews[i], cardViews[i], map.get(i));
        }

        edicoesViagem();
    }

    private void defineFuncaoArrow(ImageButton arrow, LinearLayout hiddenView, CardView cardView, Object model){
        if(model instanceof GasolinaModel){
            if(gasolinaModel.getAdicionouViagem() != 0) {
                setDadosGasolina();
                expandeCardView(arrow, hiddenView, cardView);
            }
            else{
                arrow.setAlpha(0.3F);
                arrow.setOnClickListener(view ->{
                    Toast.makeText(ResumoViagemActivity.this, R.string.gasolinaAviso, Toast.LENGTH_SHORT).show();
                });
            }
        } else if(model instanceof TarifaAereaModel){
            if(tarifaAereaModel.getAdicionouViagem() != 0) {
                setDadosTarifasAereas();
                expandeCardView(arrow, hiddenView, cardView);
            }
            else{
                arrow.setAlpha(0.3F);
                arrow.setOnClickListener(view ->{
                    Toast.makeText(ResumoViagemActivity.this, R.string.tarifaAereaAviso, Toast.LENGTH_SHORT).show();
                });
            }
        } else if(model instanceof RefeicoesModel){
            if(refeicoesModel.getAdicionouViagem() != 0) {
                setDadosRefeicoes();
                expandeCardView(arrow, hiddenView, cardView);
            }
            else{
                arrow.setAlpha(0.3F);
                arrow.setOnClickListener(view ->{
                    Toast.makeText(ResumoViagemActivity.this, R.string.refeicoesAviso, Toast.LENGTH_SHORT).show();
                });
            }
        }else if(model instanceof HospedagemModel){
            if(hospedagemModel.getAdicionouViagem() != 0) {
                setDadoshospedagem();
                expandeCardView(arrow, hiddenView, cardView);
            }
            else{
                arrow.setAlpha(0.3F);
                arrow.setOnClickListener(view ->{
                    Toast.makeText(ResumoViagemActivity.this, R.string.hospedagemAviso, Toast.LENGTH_SHORT).show();
                });
            }
        } else if(model instanceof EntretenimentoModel){
            if(entretenimentoModel.getAdicionouViagem() != 0) {
                setDadosEntreterimento();
                expandeCardView(arrow, hiddenView, cardView);
            }
            else{
                arrow.setAlpha(0.3F);
                arrow.setOnClickListener(view ->{
                    Toast.makeText(ResumoViagemActivity.this, R.string.entreterimentoAviso, Toast.LENGTH_SHORT).show();
                });
            }
        }

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

    private void setDadosViagem(){
        DecimalFormat df = new DecimalFormat("0.00");
        double valorPorPessoa = viagemModel.getTotal() / viagemModel.getTotalViajantes();

        txtTituloViagem.setText(viagemModel.getTitulo());
        txtDataCriacaoViagem.setText(viagemModel.getDataCriacao());
        txtNumeroViajantesViagem.setText((viagemModel.getTotalViajantes()) + " " + getString(R.string.viajantes));
        txtDuracaoViagem.setText((viagemModel.getDuracao()) + " " + getString(R.string.dias));
        txtValorPorPessoa.setText( df.format(valorPorPessoa) + " " + getString(R.string.porPessoa));
        txtTotalViagem.setText(String.valueOf( df.format(viagemModel.getTotal())));

    }


    private  void setarIds(){
        toolbar = findViewById(R.id.toolbarResumoViagem);
        txtTituloViagem = findViewById(R.id.resumo_tituloViagem);
        txtDataCriacaoViagem = findViewById(R.id.resumo_textDataCriacaoViagem);
        txtNumeroViajantesViagem = findViewById(R.id.resumo_textNumeroViajantesViagem);
        txtDuracaoViagem = findViewById(R.id.resumo_textDuracaoViagem);
        txtValorPorPessoa = findViewById(R.id.resumo_textValorPorPessoa);
        txtTotalViagem = findViewById(R.id.resumo_textTotalViagem);
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
        txtCustoPorPessoa = findViewById(R.id.resumo_custoEstimadoPessoa);
        txtAluguelVeiculo = findViewById(R.id.resumo_aluguelVeiculos);
        txtCustoRefeicao = findViewById(R.id.resumo_custoEstimadoRefeicao);
        txtRefeicoesDia = findViewById(R.id.resumo_refeicoesPorDia);
        txtCustoMedioNoite = findViewById(R.id.resumo_custoMedioNoite);
        txtTotalNoites = findViewById(R.id.resumo_totalNoites);
        txtTotalQuartos = findViewById(R.id.resumo_totalQuartos);
        txtValorEntreterimento1 = findViewById(R.id.resumo_valorEntreterimento1);
        txtValorEntreterimento2 = findViewById(R.id.resumo_valorEntreterimento2);
        txtValorEntreterimento3 = findViewById(R.id.resumo_valorEntreterimento3);
        txtEntreterimento1 = findViewById(R.id.resumo_txtEntreterimento1);
        txtEntreterimento2 = findViewById(R.id.resumo_txtEntreterimento2);
        txtEntreterimento3 = findViewById(R.id.resumo_txtEntreterimento3);
        txtTotalFinalGasolina = findViewById(R.id.resumo_totalFinalGasolina);
        txtTotalFinalTarifasAereas = findViewById(R.id.resumo_totalFinalTarifasAereas);
        txtTotalFinalRefeicoes = findViewById(R.id.resumo_totalFinalRefeicoes);
        txtTotalFinalHospedagem = findViewById(R.id.resumo_totalFinalHospedagem);
        txtTotalFinalEntreterimento = findViewById(R.id.resumo_totalFinalEntreterimento);
        resumoLinearEnt1 = findViewById(R.id.resumo_LinearEnt1);
        resumoLinearEnt2 = findViewById(R.id.resumo_LinearEnt2);
        resumoLinearEnt3 = findViewById(R.id.resumo_LinearEnt3);
        editBtn = findViewById(R.id.resumo_editBtn);
        deleteBtn = findViewById(R.id.resumo_deleteBtn);

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

        map = new HashMap<>();
        map.put(0, gasolinaModel);
        map.put(1, tarifaAereaModel);
        map.put(2, refeicoesModel);
        map.put(3, hospedagemModel);
        map.put(4, entretenimentoModel);
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
        txtMediaKmsLitro.setText(String.valueOf(gasolinaModel.getMediaKmsLitro()));
        txtCustoMedioLitro.setText(String.valueOf(gasolinaModel.getCustoMedioLitro()));
        txtTotalVeiculos.setText(String.valueOf(gasolinaModel.getTotalVeiculos()));
        txtTotalFinalGasolina.setText(String.valueOf(gasolinaModel.getTotal()));
    }

    private void setDadosTarifasAereas(){
        txtCustoPorPessoa.setText(String.valueOf(tarifaAereaModel.getCustoEstimadoPessoa()));
        txtAluguelVeiculo.setText(String.valueOf(tarifaAereaModel.getAluguelVeiculo()));
        txtTotalFinalTarifasAereas.setText(String.valueOf(tarifaAereaModel.getTotal()));
    }

    private void setDadoshospedagem(){
        txtCustoMedioNoite.setText(String.valueOf(hospedagemModel.getCustoMedioNoite()));
        txtTotalNoites.setText(String.valueOf(hospedagemModel.getTotalNoites()));
        txtTotalQuartos.setText(String.valueOf(hospedagemModel.getTotalQuartos()));
        txtTotalFinalHospedagem.setText(String.valueOf(hospedagemModel.getTotal()));
    }

    private void setDadosRefeicoes(){
        txtCustoRefeicao.setText(String.valueOf(refeicoesModel.getCustoEstimadoRefeicao()));
        txtRefeicoesDia.setText(String.valueOf(refeicoesModel.getRefeicoesDia()));
        txtTotalFinalRefeicoes.setText(String.valueOf(refeicoesModel.getTotal()));
    }

    private void setDadosEntreterimento(){

            txtEntreterimento1.setText(String.valueOf(entretenimentoModel.getEntretenimento1()));
            txtEntreterimento2.setText(String.valueOf(entretenimentoModel.getEntretenimento2()));
            txtEntreterimento3.setText(String.valueOf(entretenimentoModel.getEntretenimento3()));
            txtValorEntreterimento1.setText((String.valueOf(entretenimentoModel.getValorEntretenimento1())));
            txtValorEntreterimento2.setText((String.valueOf(entretenimentoModel.getValorEntretenimento2())));
            txtValorEntreterimento3.setText((String.valueOf(entretenimentoModel.getValorEntretenimento3())));

            if (entretenimentoModel.getEntretenimento1().equals("")) {
                resumoLinearEnt1.setVisibility(View.GONE);
            }

            if (entretenimentoModel.getEntretenimento2().equals("")) {
                resumoLinearEnt2.setVisibility(View.GONE);
            }

            if (entretenimentoModel.getEntretenimento3().equals("")) {
                resumoLinearEnt3.setVisibility(View.GONE);
            }

            txtTotalFinalEntreterimento.setText(String.valueOf(entretenimentoModel.getTotal()));
    }

    private void edicoesViagem(){
        editBtn.setOnClickListener(view -> {
            Integer id = Integer.valueOf(idViagem);

            Intent in = new Intent(ResumoViagemActivity.this, CadastroViagensActivity.class);
            in.putExtra("EDITAR", true);
            in.putExtra("ID_VIAGEM", id);

            startActivity(in);
        });

        deleteBtn.setOnClickListener(view -> {

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
