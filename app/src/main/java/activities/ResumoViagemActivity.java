package activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cheaptrip.R;

public class ResumoViagemActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_viagem);

        setarIds();

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private  void setarIds(){
        toolbar = findViewById(R.id.toolbarResumoViagem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
