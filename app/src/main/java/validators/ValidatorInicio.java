package validators;

import android.app.Activity;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ValidatorInicio implements IValidator {
    private final EditText editTextTituloViagem, editTextTotalViajantes, editTextDuracaoViagem;
    private final Activity activity;

    public ValidatorInicio(Activity activity) {
        this.activity = activity;
        this.editTextTituloViagem = activity.findViewById(R.id.edit_text_titulo_viagem);
        this.editTextTotalViajantes = activity.findViewById(R.id.edit_text_numero_viajantes);
        editTextDuracaoViagem = activity.findViewById(R.id.edit_text_duracao_viagem);
    }

    @Override
    public boolean valida() {
        if (editTextTituloViagem.getText().toString().isEmpty()) {
            editTextTituloViagem.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalViajantes.getText().toString().isEmpty()) {
            editTextTotalViajantes.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextDuracaoViagem.getText().toString().isEmpty()) {
            editTextDuracaoViagem.setError(activity.getResources().getString(R.string.campoObrigatorio));
            return false;
        } else if (editTextTotalViajantes.getText().toString().startsWith("0")) {
            editTextTotalViajantes.setError(activity.getResources().getString(R.string.valorInvalido));
            return false;
        } else if (editTextDuracaoViagem.getText().toString().startsWith("0")) {
            editTextDuracaoViagem.setError(activity.getResources().getString(R.string.valorInvalido));
            return false;
        }

        return true;
    }
}
