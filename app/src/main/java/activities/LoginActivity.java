package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cheaptrip.R;

import database.dao.UsuarioDAO;
import database.model.UsuarioModel;

public class LoginActivity extends AppCompatActivity {

    private TextView cadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cadastro = findViewById(R.id.mensagemCadastro);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(it);
            }
        });

        UsuarioModel u = new UsuarioModel();
        u.setNomeCompleto("Vinicius");
        u.setEmail("Vinicius@email.com");
        u.setUsuario("vinicius");
        u.setSenha("123");

        UsuarioDAO usuarioDAO = new UsuarioDAO(LoginActivity.this);
        long insert = usuarioDAO.insert(u);

        Log.d("Debuggandooo", String.valueOf(insert));
    }

}