package listeners.refeicoes;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.cheaptrip.R;

import java.text.DecimalFormat;

import util.StringUtil;

public class ListenerEditTextRefeicoes implements TextWatcher {
    private EditText editText, editTextCustoEstimadoRefeicao, editTextRefeicoesDia, editTextTotalViajantes, editTextDuracaoViagem, editTextTotalRefeicoes;
    private Double custoEstimadoRefeicao;
    private Integer totalViajantes, refeicoesDia, duracaoViagem;
    private Activity activity;
    private int ID_EDIT_TEXT_EDITANDO;

    public ListenerEditTextRefeicoes(EditText editText, Activity activity) {
        this.editText = editText;
        this.activity = activity;
        this.ID_EDIT_TEXT_EDITANDO = editText.getId();
        this.populaEditTexts();
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String valorString = editText.getText().toString();

        try {
            if (ID_EDIT_TEXT_EDITANDO == editTextCustoEstimadoRefeicao.getId()) {
                custoEstimadoRefeicao = Double.parseDouble(StringUtil.isEmpty(valorString) ? "0" : valorString);
            } else if (ID_EDIT_TEXT_EDITANDO == editTextRefeicoesDia.getId()) {
                refeicoesDia = Integer.parseInt(valorString);
            }
        } catch (NumberFormatException e) {
            return;
        }

        atualizaVariaveis();
        calcularTotalRefeicoes();
    }

    private void calcularTotalRefeicoes() {
        Double totalRefeicoes = ((refeicoesDia * totalViajantes) * custoEstimadoRefeicao) * duracaoViagem;
        DecimalFormat df = new DecimalFormat("0.00");

        if (totalRefeicoes.isInfinite() || totalRefeicoes.isNaN()) {
            editTextTotalRefeicoes.setText(R.string.valorInvalido);
            return;
        }

        editTextTotalRefeicoes.setText(df.format(totalRefeicoes));
    }

    private void populaEditTexts() {
        editTextCustoEstimadoRefeicao = activity.findViewById(R.id.edit_text_custo_estimado_refeicao);
        editTextRefeicoesDia = activity.findViewById(R.id.edit_text_refeicoes_dia);
        editTextTotalViajantes = activity.findViewById(R.id.edit_text_numero_viajantes);
        editTextDuracaoViagem = activity.findViewById(R.id.edit_text_duracao_viagem);
        editTextTotalRefeicoes = activity.findViewById(R.id.edit_text_total_refeicoes);
    }

    private void atualizaVariaveis() {
        String custoEstimadoRefeicaoString = editTextCustoEstimadoRefeicao.getText().toString();
        String refeicoesDiaString = editTextRefeicoesDia.getText().toString();
        String totalViajantesString = editTextTotalViajantes.getText().toString();
        String duracaoViagemString = editTextDuracaoViagem.getText().toString();

        try {
            custoEstimadoRefeicao = Double.valueOf(StringUtil.isEmpty(custoEstimadoRefeicaoString) ? "0" : custoEstimadoRefeicaoString);
            refeicoesDia = Integer.parseInt(StringUtil.isEmpty(refeicoesDiaString) ? "0" : refeicoesDiaString);
            totalViajantes = Integer.parseInt(StringUtil.isEmpty(totalViajantesString) ? "0" : totalViajantesString);
            duracaoViagem = Integer.parseInt(StringUtil.isEmpty(duracaoViagemString) ? "0" : duracaoViagemString);
        } catch (NumberFormatException ignored) {
            editTextTotalRefeicoes.setText(activity.getResources().getString(R.string.valorInvalido));
        }
    }
}
