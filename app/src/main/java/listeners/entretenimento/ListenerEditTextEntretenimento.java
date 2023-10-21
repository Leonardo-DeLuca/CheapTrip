package listeners.entretenimento;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.cheaptrip.R;

import util.StringUtil;

public class ListenerEditTextEntretenimento implements TextWatcher {
    private EditText editText, editTextValorEntretenimento1, editTextValorEntretenimento2, editTextValorEntretenimento3, editTextTotalEntretenimento;
    private Double valorEntretenimento1, valorEntretenimento2, valorEntretenimento3;
    private Activity activity;
    private int ID_EDIT_TEXT_EDITANDO;

    public ListenerEditTextEntretenimento(EditText editText, Activity activity) {
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
        Double valorDouble;

        try {
            valorDouble = Double.valueOf(StringUtil.isEmpty(valorString) ? "0" : valorString);
        } catch (NumberFormatException e) {
            return;
        }

        atualizaVariaveis();

        if (ID_EDIT_TEXT_EDITANDO == editTextValorEntretenimento1.getId()) {
            valorEntretenimento1 = valorDouble;
        } else if (ID_EDIT_TEXT_EDITANDO == editTextValorEntretenimento2.getId()) {
            valorEntretenimento2 = valorDouble;
        } else if (ID_EDIT_TEXT_EDITANDO == editTextValorEntretenimento3.getId()) {
            valorEntretenimento3 = valorDouble;
        }

        calcularTotalEntretenimento();
    }

    private void calcularTotalEntretenimento() {
        Double totalEntretenimento = valorEntretenimento1 + valorEntretenimento2 + valorEntretenimento3;

        if (totalEntretenimento.isInfinite() || totalEntretenimento.isNaN()) {
            editTextTotalEntretenimento.setText(R.string.valorInvalido);
            return;
        }

        editTextTotalEntretenimento.setText(String.valueOf(totalEntretenimento));
    }

    private void populaEditTexts() {
        editTextValorEntretenimento1 = activity.findViewById(R.id.edit_text_valor_entretenimento_1);
        editTextValorEntretenimento2 = activity.findViewById(R.id.edit_text_valor_entretenimento_2);
        editTextValorEntretenimento3 = activity.findViewById(R.id.edit_text_valor_entretenimento_3);
        editTextTotalEntretenimento = activity.findViewById(R.id.edit_text_total_entretenimento);
    }

    private void atualizaVariaveis() {
        String valorEntretenimento1String = editTextValorEntretenimento1.getText().toString();
        String valorEntretenimento2String = editTextValorEntretenimento2.getText().toString();
        String valorEntretenimento3String = editTextValorEntretenimento3.getText().toString();

        try {
            valorEntretenimento1 = Double.valueOf(StringUtil.isEmpty(valorEntretenimento1String) ? "0" : valorEntretenimento1String);
            valorEntretenimento2 = Double.valueOf(StringUtil.isEmpty(valorEntretenimento2String) ? "0" : valorEntretenimento2String);
            valorEntretenimento3 = Double.valueOf(StringUtil.isEmpty(valorEntretenimento3String) ? "0" : valorEntretenimento3String);
        } catch (NumberFormatException ignored) {
            editTextTotalEntretenimento.setText(activity.getResources().getString(R.string.valorInvalido));
        }
    }
}
