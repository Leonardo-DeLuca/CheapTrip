package listeners.inicio;

import android.view.View;
import android.widget.EditText;

public class ListenerEditTextInicio implements View.OnFocusChangeListener {
    private EditText[] editTextsAtualizarValor;

    public ListenerEditTextInicio(EditText[] editTextsAtualizarValor) {
        this.editTextsAtualizarValor = editTextsAtualizarValor;
    }
    @Override
    public void onFocusChange(View view, boolean isFocused) {
        if (!isFocused) {
            for (int i = 0; i < editTextsAtualizarValor.length; i++) {
                editTextsAtualizarValor[i].setText(editTextsAtualizarValor[i].getText().toString());
            }
        }
    }
}
