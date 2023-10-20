package activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cheaptrip.R;
import com.example.cheaptrip.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import database.dao.UsuarioDAO;
import database.model.UsuarioModel;
import util.KeysUtil;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private SharedPreferences preferences;
    private UsuarioDAO dao;

    private TextView nomeHeader;
    private TextView usuarioHeader;
    private TextView emailHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Integer id = preferences.getInt(KeysUtil.ID_USER_LOGIN, -1);
        dao = new UsuarioDAO(MainActivity.this);
        UsuarioModel usuario = dao.selectById(id);

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(KeysUtil.USER_LOGADO, usuario.getUsuario());
        edit.putString(KeysUtil.EMAIL_LOGADO, usuario.getEmail());
        edit.putString(KeysUtil.NOME_LOGADO, usuario.getNomeCompleto());
        edit.apply();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_viagens, R.id.nav_configuracoes).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        nomeHeader = findViewById(R.id.nomeHeader);
        nomeHeader.setText(preferences.getString(KeysUtil.NOME_LOGADO, "nome"));
        emailHeader = findViewById(R.id.emailHeader);
        emailHeader.setText(preferences.getString(KeysUtil.EMAIL_LOGADO, "email"));
        usuarioHeader = findViewById(R.id.usuarioHeader);
        usuarioHeader.setText(preferences.getString(KeysUtil.USER_LOGADO, "usuario"));

        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}