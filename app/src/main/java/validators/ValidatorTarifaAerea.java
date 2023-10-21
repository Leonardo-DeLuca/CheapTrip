package validators;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ValidatorTarifaAerea implements IValidator {
    private final EditText editTextCustoEstimadoTarifaAerea, editTextAluguelVeiculo, editTextTotalTarifaAerea;
    private CheckBox checkBoxAddViagemTarifaAerea;
    private final Activity activity;

    public ValidatorTarifaAerea(Activity activity) {
        this.activity = activity;
        this.editTextCustoEstimadoTarifaAerea = activity.findViewById(R.id.edit_text_custo_estimado_tarifa_aerea);
        this.editTextAluguelVeiculo = activity.findViewById(R.id.edit_text_custo_aluguel_veiculo);
        this.editTextTotalTarifaAerea = activity.findViewById(R.id.edit_text_total_tarifa_aerea);
        this.checkBoxAddViagemTarifaAerea = activity.findViewById(R.id.check_tarifa_aerea);
    }

    @Override
    public boolean valida() {
        if (!checkBoxAddViagemTarifaAerea.isChecked()) return true;

        if (editTextCustoEstimadoTarifaAerea.getText().toString().isEmpty()) {
            editTextCustoEstimadoTarifaAerea.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextAluguelVeiculo.getText().toString().isEmpty()) {
            editTextAluguelVeiculo.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalTarifaAerea.getText().toString().equals(activity.getResources().getString(R.string.valorInvalido))) {
            return false;
        }

        return true;
    }
}
