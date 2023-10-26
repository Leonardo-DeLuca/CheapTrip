package listeners.hospedagem;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.cheaptrip.R;

import java.text.DecimalFormat;

import util.StringUtil;

public class ListenerEditTextHospedagem implements TextWatcher {
    private EditText editText, editTextCustoMedioNoite, editTextTotalNoites, editTextTotalQuartos, editTextTotalHospedagem;
    private Double custoMedioNoite;
    private Integer totalNoites, totalQuartos;
    private Activity activity;
    private int ID_EDIT_TEXT_EDITANDO;

    public ListenerEditTextHospedagem(EditText editText, Activity activity) {
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

        try {
            if (ID_EDIT_TEXT_EDITANDO == editTextCustoMedioNoite.getId()) {
                custoMedioNoite = Double.parseDouble(StringUtil.isEmpty(valorString) ? "0" : valorString);
            } else if (ID_EDIT_TEXT_EDITANDO == editTextTotalNoites.getId()) {
                totalNoites = Integer.parseInt(valorString);
            } else if (ID_EDIT_TEXT_EDITANDO == editTextTotalQuartos.getId()) {
                totalQuartos = Integer.parseInt(valorString);
            }
        } catch (NumberFormatException e) {
            return;
        }

        atualizaVariaveis();
        calcularTotalHospedagem();
    }

    private void calcularTotalHospedagem() {
        Double totalHospedagem = (custoMedioNoite * totalNoites) * totalQuartos;
        DecimalFormat df = new DecimalFormat("0.00");

        if (totalHospedagem.isInfinite() || totalHospedagem.isNaN()) {
            editTextTotalHospedagem.setText(R.string.valorInvalido);
            return;
        }

        editTextTotalHospedagem.setText(df.format(totalHospedagem));
    }

    private void populaEditTexts() {
        editTextCustoMedioNoite = activity.findViewById(R.id.edit_text_custo_medio_noite);
        editTextTotalNoites = activity.findViewById(R.id.edit_text_total_noites);
        editTextTotalQuartos = activity.findViewById(R.id.edit_text_total_quartos);
        editTextTotalHospedagem = activity.findViewById(R.id.edit_text_total_hospedagem);
    }

    private void atualizaVariaveis() {
        String custoMedioNoiteString = editTextCustoMedioNoite.getText().toString();
        String totalNoitesString = editTextTotalNoites.getText().toString();
        String totalQuartosString = editTextTotalQuartos.getText().toString();

        try {
            custoMedioNoite = Double.valueOf(StringUtil.isEmpty(custoMedioNoiteString) ? "0" : custoMedioNoiteString);
            totalNoites = Integer.parseInt(StringUtil.isEmpty(totalNoitesString) ? "0" : totalNoitesString);
            totalQuartos = Integer.parseInt(StringUtil.isEmpty(totalQuartosString) ? "0" : totalQuartosString);
        } catch (NumberFormatException ignored) {
            editTextTotalHospedagem.setText(activity.getResources().getString(R.string.valorInvalido));
        }
    }
}
