package activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.cheaptrip.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

import listeners.entretenimento.ListenerCheckBoxEntretenimento;
import listeners.entretenimento.ListenerEditTextEntretenimento;
import listeners.geral.ListenerCheckBox;
import listeners.gasolina.ListenerEditTextGasolina;
import listeners.hospedagem.ListenerEditTextHospedagem;
import listeners.refeicoes.ListenerEditTextRefeicoes;
import listeners.tarifaAerea.ListenerEditTextTarifaAerea;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_viagens);

        setaElementos();
        setaListeners();
        montaEtapas();

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
        Toast.makeText(CadastroViagensActivity.this, "Teste", Toast.LENGTH_SHORT).show();
    }
}