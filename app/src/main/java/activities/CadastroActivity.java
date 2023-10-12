package activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheaptrip.R;
import com.google.android.material.textfield.TextInputEditText;

import database.dao.UsuarioDAO;
import database.model.UsuarioModel;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText nome;
    private TextInputEditText usuario;
    private TextInputEditText email;
    private TextInputEditText senha;
    private TextInputEditText confirmaSenha;
    private Button btnConcluir;

    private UsuarioDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        setarIds();

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dao = new UsuarioDAO(CadastroActivity.this);

                if (nome.getText().toString().isEmpty()) {
                    nome.setError("Campo nome obrigatório!");
                    nome.setEnabled(true);
                    return;
                }
                else if (usuario.getText().toString().isEmpty()) {
                    usuario.setError("Campo usuario obrigatório!");
                    usuario.setEnabled(true);
                    return;
                }
                 else if (email.getText().toString().isEmpty()) {
                    email.setError("Campo e-mail obrigatório!");
                    email.setEnabled(true);
                    return;
                }
                else if (senha.getText().toString().isEmpty()) {
                    senha.setError("Campo senha obrigatório!");
                    senha.setEnabled(true);
                    return;
                }
                else if (!senha.getText().toString().equals(confirmaSenha.getText().toString())) {
                    confirmaSenha.requestFocus();
                    Toast.makeText(CadastroActivity.this, "Os campos de senha não coincidem!", Toast.LENGTH_LONG).show();
                    return;
                }

                UsuarioModel usuarioModel = dao.selectByUsuario(usuario.getText().toString());
                if(usuarioModel != null){
                    usuario.setError("Usuario já existente!");
                    return;
                }

                usuarioModel = dao.selectByEmail(email.getText().toString());
                if(usuarioModel != null){
                    email.setError("E-mail já existente!");
                    return;
                }

                UsuarioModel user = new UsuarioModel();

                user.setNomeCompleto(nome.getText().toString());
                user.setUsuario(usuario.getText().toString());
                user.setSenha(senha.getText().toString());
                user.setEmail(email.getText().toString());

                dao.insert(user);
                Toast.makeText(CadastroActivity.this, "Cadastro Realizado com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }

    private void setarIds(){
        nome = findViewById(R.id.nomeCadastro);
        usuario = findViewById(R.id.userCadastro);
        email = findViewById(R.id.emailCadastro);
        senha = findViewById(R.id.senhaCadastro);
        confirmaSenha = findViewById(R.id.senhaRepeatCadastro);
        btnConcluir = findViewById(R.id.concluirCadastroButton);
    }
}
