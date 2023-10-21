package validators;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ValidatorRefeicoes implements IValidator {
    private EditText editTextCustoEstimadoRefeicao, editTextRefeicoesDia, editTextTotalRefeicoes;
    private CheckBox checkBoxAddViagemRefeicoes;
    private final Activity activity;

    public ValidatorRefeicoes(Activity activity) {
        this.activity = activity;
        this.editTextCustoEstimadoRefeicao = activity.findViewById(R.id.edit_text_custo_estimado_refeicao);
        this.editTextRefeicoesDia = activity.findViewById(R.id.edit_text_refeicoes_dia);
        this.editTextTotalRefeicoes = activity.findViewById(R.id.edit_text_total_refeicoes);
        this.checkBoxAddViagemRefeicoes = activity.findViewById(R.id.check_refeicoes);
    }

    @Override
    public boolean valida() {
        if (!checkBoxAddViagemRefeicoes.isChecked()) return true;

        if (editTextCustoEstimadoRefeicao.getText().toString().isEmpty()) {
            editTextCustoEstimadoRefeicao.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextRefeicoesDia.getText().toString().isEmpty()) {
            editTextRefeicoesDia.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalRefeicoes.getText().toString().equals(activity.getResources().getString(R.string.valorInvalido))) {
            return false;
        }

        return true;
    }
}
