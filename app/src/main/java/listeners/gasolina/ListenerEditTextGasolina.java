package listeners.gasolina;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.cheaptrip.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import util.StringUtil;

public class ListenerEditTextGasolina implements TextWatcher {
    private EditText editText, editTextTotalEstimadoKms, editTextMediaKmsLitro, editTextCustoMediaLitro, editTextTotalVeiculos, editTextTotalGasolina;
    private Double totalEstimadoKms, mediaKmsLitro, custoMediaLitro, totalVeiculos;
    private Activity activity;
    private int ID_EDIT_TEXT_EDITANDO;

    public ListenerEditTextGasolina(EditText editText, Activity activity) {
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

        if (ID_EDIT_TEXT_EDITANDO == editTextTotalEstimadoKms.getId()) {
            totalEstimadoKms = valorDouble;
        } else if (ID_EDIT_TEXT_EDITANDO == editTextMediaKmsLitro.getId()) {
            mediaKmsLitro = valorDouble;
        } else if (ID_EDIT_TEXT_EDITANDO == editTextCustoMediaLitro.getId()) {
            custoMediaLitro = valorDouble;
        } else if (ID_EDIT_TEXT_EDITANDO == editTextTotalVeiculos.getId()) {
            totalVeiculos = valorDouble;
        }

        calcularTotalGasolina();
    }

    private void calcularTotalGasolina() {
        Double totalGasolina = ((totalEstimadoKms / mediaKmsLitro) * custoMediaLitro) / totalVeiculos;
        DecimalFormat df = new DecimalFormat("0.00");

        if (totalGasolina.isInfinite() || totalGasolina.isNaN()) {
            editTextTotalGasolina.setText(R.string.valorInvalido);
            return;
        }

        editTextTotalGasolina.setText(df.format(totalGasolina));
    }

    private void populaEditTexts() {
        editTextTotalEstimadoKms = activity.findViewById(R.id.edit_text_total_estimado_kms);
        editTextMediaKmsLitro = activity.findViewById(R.id.edit_text_media_kms_litro);
        editTextCustoMediaLitro = activity.findViewById(R.id.edit_text_custo_medio_litro);
        editTextTotalVeiculos = activity.findViewById(R.id.edit_text_total_veiculos);
        editTextTotalGasolina = activity.findViewById(R.id.edit_text_total_gasolina);
    }

    private void atualizaVariaveis() {
        String totalEstimadoKmsString = editTextTotalEstimadoKms.getText().toString();
        String mediaKmsLitroString = editTextMediaKmsLitro.getText().toString();
        String custoMediaLitroString = editTextCustoMediaLitro.getText().toString();
        String totalVeiculosString = editTextTotalVeiculos.getText().toString();

        try {
            totalEstimadoKms = Double.valueOf(StringUtil.isEmpty(totalEstimadoKmsString) ? "0" : totalEstimadoKmsString);
            mediaKmsLitro = Double.valueOf(StringUtil.isEmpty(mediaKmsLitroString) ? "0" : mediaKmsLitroString);
            custoMediaLitro = Double.valueOf(StringUtil.isEmpty(custoMediaLitroString) ? "0" : custoMediaLitroString);
            totalVeiculos = Double.valueOf(StringUtil.isEmpty(totalVeiculosString) ? "0" : totalVeiculosString);
        } catch (NumberFormatException ignored) {
            editTextTotalGasolina.setText(activity.getResources().getString(R.string.valorInvalido));
        }

    }
}
