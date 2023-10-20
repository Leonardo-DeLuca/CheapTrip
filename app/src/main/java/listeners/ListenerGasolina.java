package listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class ListenerGasolina implements TextWatcher {
    private EditText editTextTotalEstimadoKms, editTextMediaKmsLitro, editTextCustoMediaLitro, editTextTotalVeiculos, editTextTotalGasolina;
    private Float totalEstimadoKms, mediaKmsLitro, custoMediaLitro, totalVeiculos;

    public ListenerGasolina(EditText editTextTotalEstimadoKms, EditText editTextMediaKmsLitro, EditText editTextCustoMediaLitro,
                            EditText editTextTotalVeiculos, EditText editTextTotalGasolina) {
        this.editTextTotalEstimadoKms = editTextTotalEstimadoKms;
        this.editTextMediaKmsLitro = editTextMediaKmsLitro;
        this.editTextCustoMediaLitro = editTextCustoMediaLitro;
        this.editTextTotalVeiculos = editTextTotalVeiculos;
        this.editTextTotalGasolina = editTextTotalGasolina;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        totalEstimadoKms = Float.valueOf("".equals(editTextTotalEstimadoKms.getText().toString()) ? "0" : editTextTotalEstimadoKms.getText().toString());
        mediaKmsLitro = Float.valueOf("".equals(editTextMediaKmsLitro.getText().toString()) ? "0" : editTextMediaKmsLitro.getText().toString());
        custoMediaLitro = Float.valueOf("".equals(editTextCustoMediaLitro.getText().toString()) ? "0" : editTextCustoMediaLitro.getText().toString());
        totalVeiculos = Float.valueOf("".equals(editTextTotalVeiculos.getText().toString()) ? "0" : editTextTotalVeiculos.getText().toString());

        calcularTotalGasolina();
    }

    private void calcularTotalGasolina() {
        Float totalGasolina = ((totalEstimadoKms / mediaKmsLitro) * custoMediaLitro) / totalVeiculos;

        if (totalGasolina.isInfinite() || totalGasolina.isNaN()) {
            editTextTotalGasolina.setText("Valor inválido.");
            return;
        }

        editTextTotalGasolina.setText(String.valueOf(totalGasolina));
    }
}
