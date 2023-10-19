package activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class CadastroViagensActivity extends AppCompatActivity {

    private LinearLayout containerLayout;
    private LinearLayout layoutAtual, layoutAnterior;
    private FloatingActionButton btnVoltar, btnAvancar;
    private int etapaAtual = 0;
    private HashMap<Integer, LinearLayout> etapas;
    private final int RETORNOU_ETAPA = 1;
    private final int AVANCOU_ETAPA = 2;
    private LinearLayout containerInicio, containerGasolina, containerTarifaAerea, containerEntretenimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_viagens);

        setaElementos();
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
        containerLayout = findViewById(R.id.container_layout);
        btnVoltar = findViewById(R.id.btn_voltar);
        btnAvancar = findViewById(R.id.btn_avancar);
        containerInicio = findViewById(R.id.container_inicio);
        containerGasolina = findViewById(R.id.container_gasolina);
        containerTarifaAerea = findViewById(R.id.container_tarifa_aerea);
        containerEntretenimento = findViewById(R.id.container_entretenimento);
    }

    private void finalizaCadastro() {
        Toast.makeText(CadastroViagensActivity.this, "Teste", Toast.LENGTH_SHORT).show();
    }
}
