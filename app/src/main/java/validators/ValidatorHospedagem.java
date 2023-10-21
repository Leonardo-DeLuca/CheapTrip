package validators;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ValidatorHospedagem implements IValidator {
    private EditText editTextCustoMedioNoite, editTextTotalNoites, editTextTotalQuartos, editTextTotalHospedagem;
    private CheckBox checkBoxAddViagemHospedagem;
    private Activity activity;

    public ValidatorHospedagem(Activity activity) {
        this.activity = activity;
        editTextCustoMedioNoite = activity.findViewById(R.id.edit_text_custo_medio_noite);
        editTextTotalNoites = activity.findViewById(R.id.edit_text_total_noites);
        editTextTotalQuartos = activity.findViewById(R.id.edit_text_total_quartos);
        editTextTotalHospedagem = activity.findViewById(R.id.edit_text_total_hospedagem);
        checkBoxAddViagemHospedagem = activity.findViewById(R.id.check_hospedagem);
    }

    @Override
    public boolean valida() {
        if (!checkBoxAddViagemHospedagem.isChecked()) return true;

        if (editTextCustoMedioNoite.getText().toString().isEmpty()) {
            editTextCustoMedioNoite.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalNoites.getText().toString().isEmpty()) {
            editTextTotalNoites.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalQuartos.getText().toString().isEmpty()) {
            editTextTotalQuartos.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalHospedagem.getText().toString().equals(activity.getResources().getString(R.string.valorInvalido))) {
            return false;
        }

        return true;
    }
}
