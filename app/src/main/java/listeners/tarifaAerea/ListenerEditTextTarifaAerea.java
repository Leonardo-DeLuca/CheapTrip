package listeners.tarifaAerea;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.cheaptrip.R;

import java.text.DecimalFormat;

import util.StringUtil;

public class ListenerEditTextTarifaAerea implements TextWatcher {
    private EditText editText, editTextCustoEstimadoTarifaAerea, editTextAluguelVeiculo, editTextTotalViajantes, editTextTotalTarifaAerea;
    private Double custoEstimadoTarifaAerea, aluguelVeiculo;
    private Integer totalViajantes;
    private Activity activity;
    private int ID_EDIT_TEXT_EDITANDO;

    public ListenerEditTextTarifaAerea(EditText editText, Activity activity) {
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
        double valorDouble;

        try {
            valorDouble = Double.parseDouble(StringUtil.isEmpty(valorString) ? "0" : valorString);
        } catch (NumberFormatException e) {
            return;
        }

        atualizaVariaveis();

        if (ID_EDIT_TEXT_EDITANDO == editTextCustoEstimadoTarifaAerea.getId()) {
            custoEstimadoTarifaAerea = valorDouble;
        } else if (ID_EDIT_TEXT_EDITANDO == editTextAluguelVeiculo.getId()) {
            aluguelVeiculo = valorDouble;
        }

        calcularTotalTarifaAerea();
    }

    private void calcularTotalTarifaAerea() {
        Double totalTarifaAerea = (custoEstimadoTarifaAerea * totalViajantes) + aluguelVeiculo;
        DecimalFormat df = new DecimalFormat("0.00");

        if (totalTarifaAerea.isInfinite() || totalTarifaAerea.isNaN()) {
            editTextTotalTarifaAerea.setText(R.string.valorInvalido);
            return;
        }

        editTextTotalTarifaAerea.setText(df.format(totalTarifaAerea));
    }

    private void populaEditTexts() {
        editTextCustoEstimadoTarifaAerea = activity.findViewById(R.id.edit_text_custo_estimado_tarifa_aerea);
        editTextAluguelVeiculo = activity.findViewById(R.id.edit_text_custo_aluguel_veiculo);
        editTextTotalViajantes = activity.findViewById(R.id.edit_text_numero_viajantes);
        editTextTotalTarifaAerea = activity.findViewById(R.id.edit_text_total_tarifa_aerea);
    }

    private void atualizaVariaveis() {
        String custoEstimadoTarifaAereaString = editTextCustoEstimadoTarifaAerea.getText().toString();
        String aluguelVeiculoString = editTextAluguelVeiculo.getText().toString();
        String totalViajantesString = editTextTotalViajantes.getText().toString();

        try {
            custoEstimadoTarifaAerea = Double.valueOf(StringUtil.isEmpty(custoEstimadoTarifaAereaString) ? "0" : custoEstimadoTarifaAereaString);
            aluguelVeiculo = Double.valueOf(StringUtil.isEmpty(aluguelVeiculoString) ? "0" : aluguelVeiculoString);
            totalViajantes = Integer.parseInt(totalViajantesString);
        } catch (NumberFormatException ignored) {
            editTextTotalTarifaAerea.setText(activity.getResources().getString(R.string.valorInvalido));
        }
    }
}
