package validators;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ValidatorGasolina implements IValidator {
    private final EditText editTextTotalEstimadoKms, editTextMediaKmsLitro, editTextCustoMediaLitro, editTextTotalVeiculos, editTextTotalGasolina;
    private final CheckBox checkBoxAddViagemGasolina;
    private final Activity activity;

    public ValidatorGasolina(Activity activity) {
        this.activity = activity;
        this.editTextTotalEstimadoKms = activity.findViewById(R.id.edit_text_total_estimado_kms);
        this.editTextMediaKmsLitro = activity.findViewById(R.id.edit_text_media_kms_litro);
        this.editTextCustoMediaLitro = activity.findViewById(R.id.edit_text_custo_medio_litro);
        this.editTextTotalVeiculos = activity.findViewById(R.id.edit_text_total_veiculos);
        this.checkBoxAddViagemGasolina = activity.findViewById(R.id.check_gasolina);
        this.editTextTotalGasolina = activity.findViewById(R.id.edit_text_total_gasolina);
    }

    @Override
    public boolean valida() {
        if (!checkBoxAddViagemGasolina.isChecked()) return true;

        if (editTextTotalEstimadoKms.getText().toString().isEmpty()) {
            editTextTotalEstimadoKms.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextMediaKmsLitro.getText().toString().isEmpty()) {
            editTextMediaKmsLitro.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextCustoMediaLitro.getText().toString().isEmpty()) {
            editTextCustoMediaLitro.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalVeiculos.getText().toString().isEmpty()) {
            editTextTotalVeiculos.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalGasolina.getText().toString().equals(activity.getResources().getString(R.string.valorInvalido))) {
            return false;
        }

        return true;
    }
}
