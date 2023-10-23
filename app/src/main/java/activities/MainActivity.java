package activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
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
import de.hdodenhof.circleimageview.CircleImageView;
import util.ImageUtil;
import util.KeysUtil;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private SharedPreferences preferences;
    private UsuarioDAO dao;
    private UsuarioModel usuario;

    private TextView nomeHeader, usuarioHeader, emailHeader;
    private View cabecalho;
    private CircleImageView fotoPerfil;

    private ImageUtil imageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageUtil = new ImageUtil();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_viagens, R.id.nav_configuracoes).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        recuperaUsuarioConectado();
        alteraDadosNaDrawer(navigationView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void recuperaUsuarioConectado() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        dao = new UsuarioDAO(MainActivity.this);

        Integer id = preferences.getInt(KeysUtil.ID_USER_LOGIN, -1);
        usuario = dao.selectBy(UsuarioModel.COLUNA_ID, String.valueOf(id));

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(KeysUtil.USER_LOGADO, usuario.getUsuario());
        edit.putString(KeysUtil.EMAIL_LOGADO, usuario.getEmail());
        edit.putString(KeysUtil.NOME_LOGADO, usuario.getNomeCompleto());
        edit.apply();
    }

    private void alteraDadosNaDrawer(NavigationView navigationView) {
        cabecalho = navigationView.getHeaderView(0);

        nomeHeader = cabecalho.findViewById(R.id.nomeHeader);
        nomeHeader.setText(preferences.getString(KeysUtil.NOME_LOGADO, "nome"));
        emailHeader = cabecalho.findViewById(R.id.emailHeader);
        emailHeader.setText(preferences.getString(KeysUtil.EMAIL_LOGADO, "email"));
        usuarioHeader = cabecalho.findViewById(R.id.usuarioHeader);
        usuarioHeader.setText(preferences.getString(KeysUtil.USER_LOGADO, "usuario"));
        fotoPerfil = cabecalho.findViewById(R.id.profile_image);

        if(usuario.getImagem() != null){
            fotoPerfil.setImageBitmap(imageUtil.bytesToImage(usuario.getImagem()));
        }
    }


}