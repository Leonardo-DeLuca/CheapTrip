package listeners.gasolina;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.EditText;

public class ListenerCheckBoxGasolina implements CompoundButton.OnCheckedChangeListener {
    private EditText[] editTexts;
    private EditText editTextTotal;
    private Activity activity;

    public ListenerCheckBoxGasolina(EditText[] editTexts, EditText editTextTotal, Activity activity) {
        this.editTexts = editTexts;
        this.editTextTotal = editTextTotal;
        this.activity = activity;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            for (EditText eT : editTexts) {
                eT.setEnabled(true);
            }
        } else {
            for (EditText eT : editTexts) {
                eT.setEnabled(false);
                eT.setText("0");
            }

            editTextTotal.setText("0");
        }
    }
}
