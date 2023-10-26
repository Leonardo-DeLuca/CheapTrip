package listeners.geral;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.cheaptrip.R;

public class ListenerCheckBox implements CompoundButton.OnCheckedChangeListener {
    private EditText[] editTexts;
    private EditText editTextTotal;
    private Activity activity;

    public ListenerCheckBox(EditText[] editTexts, EditText editTextTotal, Activity activity) {
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
                eT.setError(null);
                eT.setText("");
            }

            if (editTextTotal.getId() != R.id.edit_text_total_entretenimento) {
                editTextTotal.setText("0,00");
            }
        }
    }
}
