package activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.cheaptrip.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import database.dao.EntretenimentoDAO;
import database.dao.GasolinaDAO;
import database.dao.HospedagemDAO;
import database.dao.RefeicoesDAO;
import database.dao.TarifaAereaDAO;
import database.dao.UsuarioDAO;
import database.dao.ViagemDAO;
import database.model.EntretenimentoModel;
import database.model.GasolinaModel;
import database.model.HospedagemModel;
import database.model.RefeicoesModel;
import database.model.TarifaAereaModel;
import database.model.ViagemModel;
import listeners.entretenimento.ListenerCheckBoxEntretenimento;
import listeners.entretenimento.ListenerEditTextEntretenimento;
import listeners.geral.ListenerCheckBox;
import listeners.gasolina.ListenerEditTextGasolina;
import listeners.hospedagem.ListenerEditTextHospedagem;
import listeners.refeicoes.ListenerEditTextRefeicoes;
import listeners.tarifaAerea.ListenerEditTextTarifaAerea;
import util.KeysUtil;
import validators.IValidator;
import validators.ValidatorEntretenimento;
import validators.ValidatorGasolina;
import validators.ValidatorHospedagem;
import validators.ValidatorInicio;
import validators.ValidatorRefeicoes;
import validators.ValidatorTarifaAerea;

public class CadastroViagensActivity extends AppCompatActivity {
    // Controle de etapas
    private int etapaAtual = 0;
    private HashMap<Integer, LinearLayout> etapas;
    private HashMap<Integer, IValidator> validatorsEtapas;
    private final int RETORNOU_ETAPA = 1;
    private final int AVANCOU_ETAPA = 2;

    // Elementos gerais
    private LinearLayout layoutAtual, layoutAnterior;
    private LinearLayout containerInicio, containerGasolina, containerTarifaAerea, containerRefeicoes, containerHospedagem, containerEntretenimento;
    private FloatingActionButton btnVoltar, btnAvancar;
    private Toolbar toolbar;

    // Elementos etapa inicial
    private EditText editTextTituloViagem, editTextTotalViajantes, editTextDuracaoViagem;

    // Elementos etapa gasolina
    private EditText editTextTotalEstimadoKms, editTextMediaKmsLitro, editTextCustoMediaLitro, editTextTotalVeiculos, editTextTotalGasolina;
    private CheckBox checkBoxAddViagemGasolina;
    private EditText[] editTextsGasolina;

    // Elementos etapa tarifa aérea
    private EditText editTextCustoEstimadoTarifaAerea, editTextAluguelVeiculo, editTextTotalTarifaAerea;
    private CheckBox checkBoxAddViagemTarifaAerea;
    private EditText[] editTextsTarifaAerea;

    // Elementos etapa refeições
    private EditText editTextCustoEstimadoRefeicao, editTextRefeicoesDia, editTextTotalRefeicoes;
    private CheckBox checkBoxAddViagemRefeicoes;
    private EditText[] editTextsRefeicoes;

    // Elementos etapa hospedagem
    private EditText editTextCustoMedioNoite, editTextTotalNoites, editTextTotalQuartos, editTextTotalHospedagem;
    private CheckBox checkBoxAddViagemHospedagem;
    private EditText[] editTextsHospedagem;

    // Elementos etapa entretenimento
    private EditText editTextEntretenimento1, editTextValorEntretenimento1,
            editTextEntretenimento2, editTextValorEntretenimento2,
            editTextEntretenimento3, editTextValorEntretenimento3, editTextTotalEntretenimento;
    private CheckBox checkBoxEntretenimento1, checkBoxEntretenimento2, checkBoxEntretenimento3, checkBoxAddViagemEntretenimento;
    private EditText[] editTextsEntretenimento;

    // Variáveis extras
    private ViagemModel viagemModel;
    private GasolinaModel gasolinaModel;
    private TarifaAereaModel tarifaAereaModel;
    private RefeicoesModel refeicoesModel;
    private HospedagemModel hospedagemModel;
    private EntretenimentoModel entretenimentoModel;
    private ViagemDAO viagemDAO;
    private GasolinaDAO gasolinaDAO;
    private TarifaAereaDAO tarifaAereaDAO;
    private RefeicoesDAO refeicoesDAO;
    private HospedagemDAO hospedagemDAO;
    private EntretenimentoDAO entretenimentoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_viagens);

        setaElementos();
        setaListeners();
        setaVariaveisExtras();
        montaEtapas();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutAtual = etapas.get(0);
        mostraEtapaAtual(AVANCOU_ETAPA);

        btnVoltar.setOnClickListener(v -> {
            if (etapaAtual > 0) {
                retornaEtapa();
            }
        });

        btnAvancar.setOnClickListener(v -> {
            if (!validaEtapaAntesDeAvancar()) return;

            if (isUltimaEtapa()) {
                finalizaCadastro();
            } else {
                avancaEtapa();
            }
        });

        if (isEditando()) {
            alteraTituloToolbar();
            setaInformacoesParaEdicao();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void mostraEtapaAtual(int acaoEtapa) {
        if (layoutAnterior != null) {
            layoutAnterior.setVisibility(View.GONE);
        }

        if (layoutAtual != null) {
            montaAnimacaoEtapa(acaoEtapa);
            layoutAtual.setVisibility(View.VISIBLE);
        }

        btnVoltar.setVisibility(etapaAtual > 0 ? View.VISIBLE : View.INVISIBLE);
        btnAvancar.setImageResource(isUltimaEtapa() ? R.drawable.ic_finish : R.drawable.ic_next);
    }

    private void montaAnimacaoEtapa(int acaoEtapa) {
        int direcaoAnimacao = acaoEtapa == AVANCOU_ETAPA ? Gravity.END : Gravity.START;

        Transition transition = new Slide(direcaoAnimacao);
        transition.setDuration(300);
        transition.addTarget(layoutAtual);

        TransitionManager.beginDelayedTransition((ViewGroup) layoutAtual.getParent(), transition);
    }

    private void avancaEtapa() {
        layoutAnterior = etapas.get(etapaAtual);
        etapaAtual++;
        layoutAtual = etapas.get(etapaAtual);

        mostraEtapaAtual(AVANCOU_ETAPA);
    }

    private void retornaEtapa() {
        layoutAnterior = etapas.get(etapaAtual);
        etapaAtual--;
        layoutAtual = etapas.get(etapaAtual);

        mostraEtapaAtual(RETORNOU_ETAPA);
    }

    private void montaEtapas() {
        etapas = new HashMap<>();

        etapas.put(0, containerInicio);
        etapas.put(1, containerGasolina);
        etapas.put(2, containerTarifaAerea);
        etapas.put(3, containerRefeicoes);
        etapas.put(4, containerHospedagem);
        etapas.put(5, containerEntretenimento);

        validatorsEtapas = new HashMap<>();

        validatorsEtapas.put(0, new ValidatorInicio(this));
        validatorsEtapas.put(1, new ValidatorGasolina(this));
        validatorsEtapas.put(2, new ValidatorTarifaAerea(this));
        validatorsEtapas.put(3, new ValidatorRefeicoes(this));
        validatorsEtapas.put(4, new ValidatorHospedagem(this));
        validatorsEtapas.put(5, new ValidatorEntretenimento(this));
    }

    private boolean validaEtapaAntesDeAvancar() {
        IValidator validatorEtapaAtual = validatorsEtapas.get(etapaAtual);

        return validatorEtapaAtual == null || validatorEtapaAtual.valida();
    }

    private boolean isUltimaEtapa() {
        return etapaAtual == etapas.entrySet().size() - 1;
    }

    private void setaVariaveisExtras() {
        Context context = CadastroViagensActivity.this;

        viagemModel = new ViagemModel();
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

    private void setaElementos() {
        setaElementosGerais();
        setaElementosInicio();
        setaElementosGasolina();
        setaElementosTarifaAerea();
        setaElementosRefeicoes();
        setaElementosHospedagem();
        setaElementosEntretenimento();
    }

    private void setaListeners() {
        setaListenersGasolina();
        setaListenersTarifaAerea();
        setaListenersRefeicoes();
        setaListenersHospedagem();
        setaListenersEntretenimento();
    }

    private void setaElementosGerais() {
        btnVoltar = findViewById(R.id.btn_voltar);
        btnAvancar = findViewById(R.id.btn_avancar);
        containerInicio = findViewById(R.id.container_inicio);
        containerGasolina = findViewById(R.id.container_gasolina);
        containerTarifaAerea = findViewById(R.id.container_tarifa_aerea);
        containerRefeicoes = findViewById(R.id.container_refeicoes);
        containerHospedagem = findViewById(R.id.container_hospedagem);
        containerEntretenimento = findViewById(R.id.container_entretenimento);
        toolbar = findViewById(R.id.toolbarViagem);
    }

    private void setaElementosInicio() {
        editTextTituloViagem = findViewById(R.id.edit_text_titulo_viagem);
        editTextTotalViajantes = findViewById(R.id.edit_text_numero_viajantes);
        editTextDuracaoViagem = findViewById(R.id.edit_text_duracao_viagem);
    }

    private void setaElementosGasolina() {
        editTextTotalEstimadoKms = findViewById(R.id.edit_text_total_estimado_kms);
        editTextMediaKmsLitro = findViewById(R.id.edit_text_media_kms_litro);
        editTextCustoMediaLitro = findViewById(R.id.edit_text_custo_medio_litro);
        editTextTotalVeiculos = findViewById(R.id.edit_text_total_veiculos);
        checkBoxAddViagemGasolina = findViewById(R.id.check_gasolina);
        editTextTotalGasolina = findViewById(R.id.edit_text_total_gasolina);

        editTextsGasolina = new EditText[]{ editTextTotalEstimadoKms, editTextMediaKmsLitro, editTextCustoMediaLitro, editTextTotalVeiculos };
    }

    private void setaListenersGasolina() {
        editTextTotalEstimadoKms.addTextChangedListener(new ListenerEditTextGasolina(editTextTotalEstimadoKms, this));
        editTextMediaKmsLitro.addTextChangedListener(new ListenerEditTextGasolina(editTextMediaKmsLitro, this));
        editTextCustoMediaLitro.addTextChangedListener(new ListenerEditTextGasolina(editTextCustoMediaLitro, this));
        editTextTotalVeiculos.addTextChangedListener(new ListenerEditTextGasolina(editTextTotalVeiculos, this));
        checkBoxAddViagemGasolina.setOnCheckedChangeListener(new ListenerCheckBox(editTextsGasolina, editTextTotalGasolina, this));
    }

    private void setaElementosTarifaAerea() {
        editTextCustoEstimadoTarifaAerea = findViewById(R.id.edit_text_custo_estimado_tarifa_aerea);
        editTextAluguelVeiculo = findViewById(R.id.edit_text_custo_aluguel_veiculo);
        checkBoxAddViagemTarifaAerea = findViewById(R.id.check_tarifa_aerea);
        editTextTotalTarifaAerea = findViewById(R.id.edit_text_total_tarifa_aerea);

        editTextsTarifaAerea = new EditText[]{ editTextCustoEstimadoTarifaAerea, editTextAluguelVeiculo };
    }

    private void setaListenersTarifaAerea() {
        editTextCustoEstimadoTarifaAerea.addTextChangedListener(new ListenerEditTextTarifaAerea(editTextCustoEstimadoTarifaAerea, this));
        editTextAluguelVeiculo.addTextChangedListener(new ListenerEditTextTarifaAerea(editTextAluguelVeiculo, this));
        checkBoxAddViagemTarifaAerea.setOnCheckedChangeListener(new ListenerCheckBox(editTextsTarifaAerea, editTextTotalTarifaAerea, this));
    }

    private void setaElementosRefeicoes() {
        editTextCustoEstimadoRefeicao = findViewById(R.id.edit_text_custo_estimado_refeicao);
        editTextRefeicoesDia = findViewById(R.id.edit_text_refeicoes_dia);
        checkBoxAddViagemRefeicoes = findViewById(R.id.check_refeicoes);
        editTextTotalRefeicoes = findViewById(R.id.edit_text_total_refeicoes);

        editTextsRefeicoes = new EditText[]{ editTextCustoEstimadoRefeicao, editTextRefeicoesDia };
    }

    private void setaListenersRefeicoes() {
        editTextCustoEstimadoRefeicao.addTextChangedListener(new ListenerEditTextRefeicoes(editTextCustoEstimadoRefeicao, this));
        editTextRefeicoesDia.addTextChangedListener(new ListenerEditTextRefeicoes(editTextRefeicoesDia, this));
        checkBoxAddViagemRefeicoes.setOnCheckedChangeListener(new ListenerCheckBox(editTextsRefeicoes, editTextTotalRefeicoes, this));
    }

    private void setaElementosHospedagem() {
        editTextCustoMedioNoite = findViewById(R.id.edit_text_custo_medio_noite);
        editTextTotalNoites = findViewById(R.id.edit_text_total_noites);
        editTextTotalQuartos = findViewById(R.id.edit_text_total_quartos);
        checkBoxAddViagemHospedagem = findViewById(R.id.check_hospedagem);
        editTextTotalHospedagem = findViewById(R.id.edit_text_total_hospedagem);

        editTextsHospedagem = new EditText[]{ editTextCustoMedioNoite, editTextTotalNoites, editTextTotalQuartos };
    }

    private void setaListenersHospedagem() {
        editTextCustoMedioNoite.addTextChangedListener(new ListenerEditTextHospedagem(editTextCustoMedioNoite, this));
        editTextTotalNoites.addTextChangedListener(new ListenerEditTextHospedagem(editTextTotalNoites, this));
        editTextTotalQuartos.addTextChangedListener(new ListenerEditTextHospedagem(editTextTotalQuartos, this));
        checkBoxAddViagemHospedagem.setOnCheckedChangeListener(new ListenerCheckBox(editTextsHospedagem, editTextTotalHospedagem, this));
    }

    private void setaElementosEntretenimento() {
        editTextEntretenimento1 = findViewById(R.id.edit_text_entretenimento_1);
        editTextValorEntretenimento1 = findViewById(R.id.edit_text_valor_entretenimento_1);
        editTextEntretenimento2 = findViewById(R.id.edit_text_entretenimento_2);
        editTextValorEntretenimento2 = findViewById(R.id.edit_text_valor_entretenimento_2);
        editTextEntretenimento3 = findViewById(R.id.edit_text_entretenimento_3);
        editTextValorEntretenimento3 = findViewById(R.id.edit_text_valor_entretenimento_3);
        editTextTotalEntretenimento = findViewById(R.id.edit_text_total_entretenimento);
        checkBoxEntretenimento1 = findViewById(R.id.check_entretenimento_1);
        checkBoxEntretenimento2 = findViewById(R.id.check_entretenimento_2);
        checkBoxEntretenimento3 = findViewById(R.id.check_entretenimento_3);
        checkBoxAddViagemEntretenimento = findViewById(R.id.check_entretenimento);

        editTextsEntretenimento = new EditText[]{ editTextEntretenimento1, editTextEntretenimento2, editTextEntretenimento3,
                editTextValorEntretenimento1, editTextValorEntretenimento2, editTextValorEntretenimento3 };
    }

    private void setaListenersEntretenimento() {
        EditText[] editTextsEntretenimento1 = new EditText[]{ editTextValorEntretenimento1, editTextEntretenimento1 };
        EditText[] editTextsEntretenimento2 = new EditText[]{ editTextValorEntretenimento2, editTextEntretenimento2 };
        EditText[] editTextsEntretenimento3 = new EditText[]{ editTextValorEntretenimento3, editTextEntretenimento3 };

        editTextValorEntretenimento1.addTextChangedListener(new ListenerEditTextEntretenimento(editTextValorEntretenimento1, this));
        editTextValorEntretenimento2.addTextChangedListener(new ListenerEditTextEntretenimento(editTextValorEntretenimento2, this));
        editTextValorEntretenimento3.addTextChangedListener(new ListenerEditTextEntretenimento(editTextValorEntretenimento3, this));
        checkBoxEntretenimento1.setOnCheckedChangeListener(new ListenerCheckBox(editTextsEntretenimento1, editTextTotalEntretenimento, this));
        checkBoxEntretenimento2.setOnCheckedChangeListener(new ListenerCheckBox(editTextsEntretenimento2, editTextTotalEntretenimento, this));
        checkBoxEntretenimento3.setOnCheckedChangeListener(new ListenerCheckBox(editTextsEntretenimento3, editTextTotalEntretenimento, this));
        checkBoxAddViagemEntretenimento.setOnCheckedChangeListener(new ListenerCheckBoxEntretenimento(editTextsEntretenimento, editTextTotalEntretenimento, this));
    }

    private void finalizaCadastro() {
        viagemModel = getViagemModel();
        long idViagem = 0;
        String editadaOuCadastrada = isEditando() ? "editada" : "cadastrada";

//        if (viagemModel.getTotal() == 0.0) {
//            Toast.makeText(CadastroViagensActivity.this, "Valor total da viagem é zero.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        idViagem = isEditando() ? getIdViagemEditando() : viagemDAO.insert(viagemModel);

        if (idViagem == 0) {
            Toast.makeText(CadastroViagensActivity.this, "Ocorreu um problema ao salvar/editar no Banco de Dados.", Toast.LENGTH_SHORT).show();
            return;
        }

        gasolinaModel = getGasolinaModel(idViagem);
        tarifaAereaModel = getTarifaAereaModel(idViagem);
        refeicoesModel = getRefeicoesModel(idViagem);
        hospedagemModel = getHospedagemModel(idViagem);
        entretenimentoModel = getEntretenimentoModel(idViagem);

        if (isEditando()) {
            viagemModel.setId((int) idViagem);
            executaUpdates();
        } else {
            executaInserts();
        }

        Toast.makeText(CadastroViagensActivity.this, "Viagem " + editadaOuCadastrada + " com sucesso!", Toast.LENGTH_SHORT).show();
        setResult(1);
        finish();
    }

    private void executaInserts() {
        gasolinaDAO.insert(gasolinaModel);
        tarifaAereaDAO.insert(tarifaAereaModel);
        refeicoesDAO.insert(refeicoesModel);
        hospedagemDAO.insert(hospedagemModel);
        entretenimentoDAO.insert(entretenimentoModel);
    }

    private void executaUpdates() {
        viagemDAO.update(viagemModel);
        gasolinaDAO.update(gasolinaModel);
        tarifaAereaDAO.update(tarifaAereaModel);
        refeicoesDAO.update(refeicoesModel);
        hospedagemDAO.update(hospedagemModel);
        entretenimentoDAO.update(entretenimentoModel);
    }

    private String getDataCriacaoViagem() {
        Locale locale = new Locale("pt", "BR");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", locale);
        Date dataCriacao = new Date(System.currentTimeMillis());

        return dateFormat.format(dataCriacao);
    }

    private ViagemModel getViagemModel() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        viagemModel.setTitulo(editTextTituloViagem.getText().toString());
        viagemModel.setIdUsuario(preferences.getInt(KeysUtil.ID_USER_LOGIN, -1));
        viagemModel.setTotalViajantes(Integer.parseInt(editTextTotalViajantes.getText().toString()));
        viagemModel.setDuracao(Integer.parseInt(editTextDuracaoViagem.getText().toString()));
        viagemModel.setDataCriacao(getDataCriacaoViagem());

        double totalGasolina = Double.parseDouble(editTextTotalGasolina.getText().toString());
        double totalTarifaAerea = Double.parseDouble(editTextTotalTarifaAerea.getText().toString());
        double totalRefeicoes = Double.parseDouble(editTextTotalRefeicoes.getText().toString());
        double totalHospedagem = Double.parseDouble(editTextTotalHospedagem.getText().toString());
        double totalEntretenimento = Double.parseDouble(editTextTotalEntretenimento.getText().toString());

        double total = totalGasolina + totalTarifaAerea + totalRefeicoes + totalHospedagem + totalEntretenimento;

        viagemModel.setTotal(total);

        return viagemModel;
    }

    private GasolinaModel getGasolinaModel(long idViagem) {
        gasolinaModel.setIdViagem((int) idViagem);

        if (checkBoxAddViagemGasolina.isChecked()) {
            gasolinaModel.setTotalEstimadoKms(Double.parseDouble(editTextTotalEstimadoKms.getText().toString()));
            gasolinaModel.setMediaKmsLitro(Double.parseDouble(editTextMediaKmsLitro.getText().toString()));
            gasolinaModel.setCustoMedioLitro(Double.parseDouble(editTextCustoMediaLitro.getText().toString()));
            gasolinaModel.setTotalVeiculos(Integer.parseInt(editTextTotalVeiculos.getText().toString()));
            gasolinaModel.setTotal(Double.parseDouble(editTextTotalGasolina.getText().toString()));
            gasolinaModel.setAdicionouViagem(1);
        } else {
            gasolinaModel.setAdicionouViagem(0);
        }

        return gasolinaModel;
    }

    private TarifaAereaModel getTarifaAereaModel(long idViagem) {
        tarifaAereaModel.setIdViagem((int) idViagem);

        if (checkBoxAddViagemTarifaAerea.isChecked()) {
            tarifaAereaModel.setCustoEstimadoPessoa(Double.parseDouble(editTextCustoEstimadoTarifaAerea.getText().toString()));
            tarifaAereaModel.setAluguelVeiculo(Double.parseDouble(editTextAluguelVeiculo.getText().toString()));
            tarifaAereaModel.setTotal(Double.parseDouble(editTextTotalTarifaAerea.getText().toString()));
            tarifaAereaModel.setAdicionouViagem(1);
        } else {
            tarifaAereaModel.setAdicionouViagem(0);
        }

        return tarifaAereaModel;
    }

    private RefeicoesModel getRefeicoesModel(long idViagem) {
        refeicoesModel.setIdViagem((int) idViagem);

        if (checkBoxAddViagemRefeicoes.isChecked()) {
            refeicoesModel.setCustoEstimadoRefeicao(Double.parseDouble(editTextCustoEstimadoRefeicao.getText().toString()));
            refeicoesModel.setRefeicoesDia(Integer.parseInt(editTextRefeicoesDia.getText().toString()));
            refeicoesModel.setTotal(Double.parseDouble(editTextTotalRefeicoes.getText().toString()));
            refeicoesModel.setAdicionouViagem(1);
        } else {
            refeicoesModel.setAdicionouViagem(0);
        }

        return refeicoesModel;
    }

    private HospedagemModel getHospedagemModel(long idViagem) {
        hospedagemModel.setIdViagem((int) idViagem);

        if (checkBoxAddViagemHospedagem.isChecked()) {
            hospedagemModel.setCustoMedioNoite(Double.parseDouble(editTextCustoMedioNoite.getText().toString()));
            hospedagemModel.setTotalNoites(Integer.parseInt(editTextTotalNoites.getText().toString()));
            hospedagemModel.setTotalQuartos(Integer.parseInt(editTextTotalNoites.getText().toString()));
            hospedagemModel.setTotal(Double.parseDouble(editTextTotalHospedagem.getText().toString()));
            hospedagemModel.setAdicionouViagem(1);
        } else {
            hospedagemModel.setAdicionouViagem(0);
        }

        return hospedagemModel;
    }

    private EntretenimentoModel getEntretenimentoModel(long idViagem) {
        entretenimentoModel.setIdViagem((int) idViagem);

        if (checkBoxAddViagemEntretenimento.isChecked()) {
            if (checkBoxEntretenimento1.isChecked()) {
                entretenimentoModel.setEntretenimento1(editTextEntretenimento1.getText().toString());
                entretenimentoModel.setValorEntretenimento1(Double.parseDouble(editTextValorEntretenimento1.getText().toString()));
            } else {
                entretenimentoModel.setEntretenimento1("");
                entretenimentoModel.setValorEntretenimento1(0);
            }

            if (checkBoxEntretenimento2.isChecked()) {
                entretenimentoModel.setEntretenimento2(editTextEntretenimento2.getText().toString());
                entretenimentoModel.setValorEntretenimento2(Double.parseDouble(editTextValorEntretenimento2.getText().toString()));
            } else {
                entretenimentoModel.setEntretenimento2("");
                entretenimentoModel.setValorEntretenimento2(0);
            }

            if (checkBoxEntretenimento3.isChecked()) {
                entretenimentoModel.setEntretenimento3(editTextEntretenimento3.getText().toString());
                entretenimentoModel.setValorEntretenimento3(Double.parseDouble(editTextValorEntretenimento3.getText().toString()));
            } else {
                entretenimentoModel.setEntretenimento3("");
                entretenimentoModel.setValorEntretenimento3(0);
            }

            entretenimentoModel.setTotal(Double.parseDouble(editTextTotalEntretenimento.getText().toString()));
            entretenimentoModel.setAdicionouViagem(1);
        } else {
            entretenimentoModel.setAdicionouViagem(0);
        }

        return entretenimentoModel;
    }

    private boolean isEditando() {
        return getIntent().getBooleanExtra("EDITAR", false);
    }

    private int getIdViagemEditando() {
        return getIntent().getIntExtra("ID_VIAGEM", 0);
    }

    private void alteraTituloToolbar() {
        toolbar.setTitle(R.string.tituloEditarViagem);
    }

    private void setaInformacoesParaEdicao() {
        int idViagem = getIdViagemEditando();

        if (idViagem != 0) {
            viagemModel = viagemDAO.selectById(String.valueOf(idViagem));
            gasolinaModel = gasolinaDAO.selectBy(GasolinaModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
            tarifaAereaModel = tarifaAereaDAO.selectBy(TarifaAereaModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
            refeicoesModel = refeicoesDAO.selectBy(RefeicoesModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
            hospedagemModel = hospedagemDAO.selectBy(HospedagemModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));
            entretenimentoModel = entretenimentoDAO.selectBy(EntretenimentoModel.COLUNA_ID_VIAGEM, String.valueOf(idViagem));

            setaInformacoesViagemParaEdicao(viagemModel);
            setaInformacoesGasolinaParaEdicao(gasolinaModel);
            setaInformacoesTarifaAereaParaEdicao(tarifaAereaModel);
            setaInformacoesRefeicoesParaEdicao(refeicoesModel);
            setaInformacoesHospedagemParaEdicao(hospedagemModel);
            setaInformacoesEntretenimentoParaEdicao(entretenimentoModel);
        } else {
            Toast.makeText(CadastroViagensActivity.this, "Erro.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setaInformacoesViagemParaEdicao(ViagemModel viagemEditando) {
        editTextTituloViagem.setText(viagemEditando.getTitulo());
        editTextTotalViajantes.setText(String.valueOf(viagemEditando.getTotalViajantes()));
        editTextDuracaoViagem.setText(String.valueOf(viagemEditando.getDuracao()));
    }

    private void setaInformacoesGasolinaParaEdicao(GasolinaModel gasolinaEditando) {
        if (gasolinaEditando.getAdicionouViagem() != 0) {
            checkBoxAddViagemGasolina.setChecked(true);
            editTextTotalEstimadoKms.setText(String.valueOf(gasolinaEditando.getTotalEstimadoKms()));
            editTextMediaKmsLitro.setText(String.valueOf(gasolinaEditando.getMediaKmsLitro()));
            editTextCustoMediaLitro.setText(String.valueOf(gasolinaEditando.getCustoMedioLitro()));
            editTextTotalVeiculos.setText(String.valueOf(gasolinaEditando.getTotalVeiculos()));
        } else {
            checkBoxAddViagemGasolina.setChecked(false);
        }
    }

    private void setaInformacoesTarifaAereaParaEdicao(TarifaAereaModel tarifaAereaEditando) {
        if (tarifaAereaEditando.getAdicionouViagem() != 0) {
            checkBoxAddViagemTarifaAerea.setChecked(true);
            editTextCustoEstimadoTarifaAerea.setText(String.valueOf(tarifaAereaEditando.getCustoEstimadoPessoa()));
            editTextAluguelVeiculo.setText(String.valueOf(tarifaAereaEditando.getAluguelVeiculo()));
        } else {
            checkBoxAddViagemTarifaAerea.setChecked(false);
        }
    }

    private void setaInformacoesRefeicoesParaEdicao(RefeicoesModel refeicoesEditando) {
        if (refeicoesEditando.getAdicionouViagem() != 0) {
            checkBoxAddViagemRefeicoes.setChecked(true);
            editTextCustoEstimadoRefeicao.setText(String.valueOf(refeicoesEditando.getCustoEstimadoRefeicao()));
            editTextRefeicoesDia.setText(String.valueOf(refeicoesEditando.getRefeicoesDia()));
        } else {
            checkBoxAddViagemRefeicoes.setChecked(false);
        }
    }

    private void setaInformacoesHospedagemParaEdicao(HospedagemModel hospedagemEditando) {
        if (hospedagemEditando.getAdicionouViagem() != 0) {
            checkBoxAddViagemHospedagem.setChecked(true);
            editTextCustoMedioNoite.setText(String.valueOf(hospedagemEditando.getCustoMedioNoite()));
            editTextTotalNoites.setText(String.valueOf(hospedagemEditando.getTotalNoites()));
            editTextTotalQuartos.setText(String.valueOf(hospedagemEditando.getTotalQuartos()));
        } else {
            checkBoxAddViagemHospedagem.setChecked(false);
        }
    }

    private void setaInformacoesEntretenimentoParaEdicao(EntretenimentoModel entretenimentoEditando) {
        if (entretenimentoEditando.getAdicionouViagem() != 0) {
            checkBoxAddViagemEntretenimento.setChecked(true);

            if (entretenimentoEditando.getEntretenimento1() != null && !"".equals(entretenimentoEditando.getEntretenimento1())) {
                checkBoxEntretenimento1.setChecked(true);
                editTextEntretenimento1.setText(entretenimentoEditando.getEntretenimento1());
                editTextValorEntretenimento1.setText(String.valueOf(entretenimentoEditando.getValorEntretenimento1()));
            } else {
                checkBoxEntretenimento1.setChecked(false);
            }

            if (entretenimentoEditando.getEntretenimento2() != null && !"".equals(entretenimentoEditando.getEntretenimento2())) {
                checkBoxEntretenimento2.setChecked(true);
                editTextEntretenimento2.setText(entretenimentoEditando.getEntretenimento2());
                editTextValorEntretenimento2.setText(String.valueOf(entretenimentoEditando.getValorEntretenimento2()));
            } else {
                checkBoxEntretenimento2.setChecked(false);
            }

            if (entretenimentoEditando.getEntretenimento3() != null && !"".equals(entretenimentoEditando.getEntretenimento3())) {
                checkBoxEntretenimento3.setChecked(true);
                editTextEntretenimento3.setText(entretenimentoEditando.getEntretenimento3());
                editTextValorEntretenimento3.setText(String.valueOf(entretenimentoEditando.getValorEntretenimento3()));
            } else {
                checkBoxEntretenimento3.setChecked(false);
            }
        } else {
            checkBoxAddViagemEntretenimento.setChecked(false);
        }
    }
}