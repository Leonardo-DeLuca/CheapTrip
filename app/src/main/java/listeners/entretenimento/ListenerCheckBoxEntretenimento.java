package listeners.entretenimento;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ListenerCheckBoxEntretenimento implements CompoundButton.OnCheckedChangeListener {
    private EditText[] editTexts;
    private EditText editTextTotal;
    private CheckBox checkBoxEntretenimento1, checkBoxEntretenimento2, checkBoxEntretenimento3, checkBoxAddViagemEntretenimento;
    private Activity activity;

    public ListenerCheckBoxEntretenimento(EditText[] editTexts, EditText editTextTotal, Activity activity) {
        this.editTexts = editTexts;
        this.editTextTotal = editTextTotal;
        this.activity = activity;
        this.checkBoxEntretenimento1 = activity.findViewById(R.id.check_entretenimento_1);
        this.checkBoxEntretenimento2 = activity.findViewById(R.id.check_entretenimento_2);
        this.checkBoxEntretenimento3 = activity.findViewById(R.id.check_entretenimento_3);
        this.checkBoxAddViagemEntretenimento = activity.findViewById(R.id.check_entretenimento);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            if (compoundButton.getId() == checkBoxAddViagemEntretenimento.getId()) {
                habilitaOutrosCheckBoxes();
            }
        } else {
            for (EditText eT : editTexts) {
                eT.setEnabled(false);
                eT.setError(null);
                eT.setText("");
            }

            editTextTotal.setText("0");

            if (compoundButton.getId() == checkBoxAddViagemEntretenimento.getId()) {
                desabilitaOutrosCheckBoxes();
            }
        }
    }

    private void habilitaOutrosCheckBoxes() {
        checkBoxEntretenimento1.setEnabled(true);
        checkBoxEntretenimento2.setEnabled(true);
        checkBoxEntretenimento3.setEnabled(true);
    }

    private void desabilitaOutrosCheckBoxes() {
        checkBoxEntretenimento1.setChecked(false);
        checkBoxEntretenimento1.setEnabled(false);
        checkBoxEntretenimento2.setChecked(false);
        checkBoxEntretenimento2.setEnabled(false);
        checkBoxEntretenimento3.setChecked(false);
        checkBoxEntretenimento3.setEnabled(false);
    }
}
