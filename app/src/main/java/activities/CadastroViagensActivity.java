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

import listeners.gasolina.ListenerCheckBoxGasolina;
import listeners.gasolina.ListenerEditTextGasolina;

public class CadastroViagensActivity extends AppCompatActivity {
    // Controle de etapas
    private int etapaAtual = 0;
    private HashMap<Integer, LinearLayout> etapas;
    private final int RETORNOU_ETAPA = 1;
    private final int AVANCOU_ETAPA = 2;

    // Elementos gerais
    private LinearLayout layoutAtual, layoutAnterior;
    private LinearLayout containerInicio, containerGasolina, containerTarifaAerea, containerEntretenimento;
    private FloatingActionButton btnVoltar, btnAvancar;
    private EditText editTextTituloViagem;

    // Elementos etapa gasolina
    private EditText editTextTotalEstimadoKms, editTextMediaKmsLitro, editTextCustoMediaLitro, editTextTotalVeiculos, editTextTotalGasolina;
    private CheckBox checkBoxAddViagemGasolina;
    private EditText[] editTextsGasolina;

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
        String tituloViagem = editTextTituloViagem.getText().toString();

        if ("".equals(tituloViagem.trim())) {
            editTextTituloViagem.setError(getResources().getString(R.string.validacaoTituloTxt));
            return;
        }

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
        etapas.put(3, containerEntretenimento);
    }

    private boolean isUltimaEtapa() {
        return etapaAtual == etapas.entrySet().size() - 1;
    }

    private void setaElementos() {
        setaElementosGerais();
        setaElementosGasolina();
    }

    private void setaListeners() {
        setaListenersGasolina();
    }

    private void setaElementosGerais() {
        btnVoltar = findViewById(R.id.btn_voltar);
        btnAvancar = findViewById(R.id.btn_avancar);
        containerInicio = findViewById(R.id.container_inicio);
        containerGasolina = findViewById(R.id.container_gasolina);
        containerTarifaAerea = findViewById(R.id.container_tarifa_aerea);
        containerEntretenimento = findViewById(R.id.container_entretenimento);
        editTextTituloViagem = findViewById(R.id.edit_text_titulo_viagem);
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
        checkBoxAddViagemGasolina.setOnCheckedChangeListener(new ListenerCheckBoxGasolina(editTextsGasolina, editTextTotalGasolina, this));
    }

    private void finalizaCadastro() {
        Toast.makeText(CadastroViagensActivity.this, "Teste", Toast.LENGTH_SHORT).show();
    }
}