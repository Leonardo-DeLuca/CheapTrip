package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cheaptrip.R;

import util.KeysUtil;

public class LoginActivity extends AppCompatActivity {

    //Definição de variáveis
    private TextView cadastro;
    private CheckBox chkBoxManterConectado;
    private Button loginBtn;
    private SharedPreferences preferences;
    private Boolean mantemConexao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor edit = preferences.edit();

        mantemConexao = preferences.getBoolean(KeysUtil.MANTER_CONEXAO, false);
        if(mantemConexao){
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(it);
        }

        else {
            setContentView(R.layout.activity_login);

            //Atribuições por id
            chkBoxManterConectado = findViewById(R.id.chkBoxManterConectado);
            cadastro = findViewById(R.id.mensagemCadastro);
            loginBtn = findViewById(R.id.loginButton);

            //Ação clicar novo cadasto
            cadastro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Chama activity novo cadastro
                    Intent it = new Intent(LoginActivity.this, RegistrarActivity.class);
                    startActivity(it);
                }
            });

            //Ação botão logar
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                //Chama função que realiza as validações de login.
                public void onClick(View v) {
                    acaoLogar(edit);
                }
            });

        }

    }

    //Função que realiza as validações de login, verifica se irá manter-se conectado e entra no aplicativo.
    private void acaoLogar (SharedPreferences.Editor edit){

//        if(chkBoxManterConectado.isChecked()){
//            edit.putBoolean(KeysUtil.MANTER_CONEXAO, true);
//            edit.apply();
//        }

        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(it);

    }

}