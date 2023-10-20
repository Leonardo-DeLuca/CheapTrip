package activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheaptrip.R;
import com.google.android.material.textfield.TextInputEditText;

import database.dao.UsuarioDAO;
import database.model.UsuarioModel;

public class RegistrarActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_registrar);

        setarIds();

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nome.getText().toString().isEmpty()) {
                    nome.setError("Campo nome obrigatório!");
                    nome.setEnabled(true);
                }
                else if (usuario.getText().toString().isEmpty()) {
                    usuario.setError("Campo usuario obrigatório!");
                    usuario.setEnabled(true);
                }
                else if (email.getText().toString().isEmpty()) {
                    email.setError("Campo e-mail obrigatório!");
                    email.setEnabled(true);
                }
                else if (senha.getText().toString().isEmpty()) {
                    senha.setError("Campo senha obrigatório!");
                    senha.setEnabled(true);
                }
                else if (!senha.getText().toString().equals(confirmaSenha.getText().toString())) {
                    confirmaSenha.requestFocus();
                    Toast.makeText(RegistrarActivity.this, "Os campos de senha não coincidem!", Toast.LENGTH_LONG).show();
                } else {
                    registrarUsuario();
                }

            }
        });
    }

    private void registrarUsuario() {
        UsuarioModel usuarioRegistrar = new UsuarioModel();
        dao = new UsuarioDAO(RegistrarActivity.this);

        UsuarioModel usuarioModel = dao.selectBy(UsuarioModel.COLUNA_USUARIO, usuario.getText().toString());
        if (usuarioModel != null) {
            usuario.setError("Usuário já existente!");
            return;
        }

        usuarioModel = dao.selectBy(UsuarioModel.COLUNA_EMAIL, email.getText().toString());
        if (usuarioModel != null) {
            email.setError("E-mail já existente!");
            return;
        }

        usuarioRegistrar.setNomeCompleto(nome.getText().toString());
        usuarioRegistrar.setUsuario(usuario.getText().toString());
        usuarioRegistrar.setSenha(senha.getText().toString());
        usuarioRegistrar.setEmail(email.getText().toString());

        dao.insert(usuarioRegistrar);
        Toast.makeText(RegistrarActivity.this, "Cadastro Realizado com sucesso!", Toast.LENGTH_LONG).show();
        finish();
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
