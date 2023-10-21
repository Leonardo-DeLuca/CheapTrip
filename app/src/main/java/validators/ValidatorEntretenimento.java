package validators;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ValidatorEntretenimento implements IValidator {
    private EditText editTextEntretenimento1, editTextEntretenimento2, editTextEntretenimento3;
    private EditText editTextValorEntretenimento1, editTextValorEntretenimento2, editTextValorEntretenimento3, editTextTotalEntretenimento;
    private CheckBox checkBoxEntretenimento1, checkBoxEntretenimento2, checkBoxEntretenimento3, checkBoxAddViagemEntretenimento;
    private Activity activity;

    public ValidatorEntretenimento(Activity activity) {
        this.activity = activity;
        this.editTextEntretenimento1 = activity.findViewById(R.id.edit_text_entretenimento_1);
        this.editTextEntretenimento2 = activity.findViewById(R.id.edit_text_entretenimento_2);
        this.editTextEntretenimento3 = activity.findViewById(R.id.edit_text_entretenimento_3);
        this.editTextValorEntretenimento1 = activity.findViewById(R.id.edit_text_valor_entretenimento_1);
        this.editTextValorEntretenimento2 = activity.findViewById(R.id.edit_text_valor_entretenimento_2);
        this.editTextValorEntretenimento3 = activity.findViewById(R.id.edit_text_valor_entretenimento_3);
        this.checkBoxEntretenimento1 = activity.findViewById(R.id.check_entretenimento_1);
        this.checkBoxEntretenimento2 = activity.findViewById(R.id.check_entretenimento_2);
        this.checkBoxEntretenimento3 = activity.findViewById(R.id.check_entretenimento_3);
        this.checkBoxAddViagemEntretenimento = activity.findViewById(R.id.check_entretenimento);
        this.editTextTotalEntretenimento = activity.findViewById(R.id.edit_text_total_entretenimento);
    }

    @Override
    public boolean valida() {
        if (!checkBoxAddViagemEntretenimento.isChecked()) return true;

        if (checkBoxEntretenimento1.isChecked()) {
            if (editTextEntretenimento1.getText().toString().isEmpty()) {
                editTextEntretenimento1.setError(activity.getResources().getString(R.string.campoObrigatorio));
                return false;
            } else if (editTextValorEntretenimento1.getText().toString().isEmpty()) {
                editTextValorEntretenimento1.setError(activity.getResources().getString(R.string.campoObrigatorio));
                return false;
            }
        } else if (checkBoxEntretenimento2.isChecked()) {
            if (editTextEntretenimento2.getText().toString().isEmpty()) {
                editTextEntretenimento2.setError(activity.getResources().getString(R.string.campoObrigatorio));
                return false;
            } else if (editTextValorEntretenimento2.getText().toString().isEmpty()) {
                editTextValorEntretenimento2.setError(activity.getResources().getString(R.string.campoObrigatorio));
                return false;
            }
        } else if (checkBoxEntretenimento3.isChecked()) {
            if (editTextEntretenimento3.getText().toString().isEmpty()) {
                editTextEntretenimento3.setError(activity.getResources().getString(R.string.campoObrigatorio));
                return false;
            } else if (editTextValorEntretenimento3.getText().toString().isEmpty()) {
                editTextValorEntretenimento3.setError(activity.getResources().getString(R.string.campoObrigatorio));
                return false;
            }
        }

         if (editTextTotalEntretenimento.getText().toString().equals(activity.getResources().getString(R.string.valorInvalido))) {
            return false;
        }

        return true;
    }
}
