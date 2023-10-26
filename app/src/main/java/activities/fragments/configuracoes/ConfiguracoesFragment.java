package activities.fragments.configuracoes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cheaptrip.R;
import com.example.cheaptrip.databinding.FragmentConfiguracoesBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import activities.LoginActivity;
import database.dao.UsuarioDAO;
import database.model.UsuarioModel;
import de.hdodenhof.circleimageview.CircleImageView;
import util.ImageUtil;
import util.KeysUtil;

public class ConfiguracoesFragment extends Fragment {
    private FragmentConfiguracoesBinding binding;
    private SharedPreferences preferences;
    private TextView user;
    private Button btnAlterarFoto, btnAlterarSenha, btnDeslogar;
    private UsuarioDAO dao;
    private TextInputEditText senha, repeteSenha;
    private CircleImageView fotoPerfil, fotoPerfilDrawer;
    private UsuarioModel usuario;
    private ImageUtil imageUtil;
    private static final int SELECIONAR_IMAGEM = 1;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfiguracoesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageUtil = new ImageUtil();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        setarIds(view);

        dao = new UsuarioDAO(getContext());
        usuario = dao.selectBy(UsuarioModel.COLUNA_ID, String.valueOf(preferences.getInt(KeysUtil.ID_USER_LOGIN, -1)));

        byte[] userImageBlob = usuario.getImagem();

        if (userImageBlob != null) {
            Bitmap imagemBytes = imageUtil.bytesToImage(userImageBlob);
            fotoPerfil.setImageBitmap(imagemBytes);
        }

        user.setText(usuario.getUsuario());

        btnDeslogar.setOnClickListener(view1 -> {
            edit.clear();
            edit.apply();
            Intent it = new Intent(view.getContext(), LoginActivity.class);
            startActivity(it);
        });

        btnAlterarSenha.setOnClickListener(view1 -> {
            view1 = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_layout, null);
            senha = view1.findViewById(R.id.editarSenha);
            repeteSenha = view1.findViewById(R.id.editarSenhaRepeat);

            final AlertDialog dialog = new AlertDialog.Builder(view1.getContext())
                    .setTitle(R.string.tituloDialogSenha)
                    .setView(view1)
                    .setPositiveButton(R.string.salvarAlteracaoSenhaBtn, null)
                    .setNegativeButton(R.string.cancelarDialogBtn, null)
                    .show();

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String senhaNova;

                    if (senha.getText().toString().isEmpty()) {
                        senha.setError(getString(R.string.campoObrigatorio));
                        Toast.makeText(getContext(), R.string.campoObrigatorio, Toast.LENGTH_LONG).show();
                        senha.setEnabled(true);
                    } else if (!senha.getText().toString().equals(repeteSenha.getText().toString())) {
                        repeteSenha.requestFocus();
                        Toast.makeText(getContext(), R.string.toastSenhasDiferentes, Toast.LENGTH_LONG).show();
                    } else {
                        senhaNova = senha.getText().toString();
                        alterarSenha(senhaNova);
                        Toast.makeText(view.getContext(), R.string.toastSenhaAlterada, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }
            });
        });

        btnAlterarFoto.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.selecionar)), SELECIONAR_IMAGEM);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setarIds(View view){
        btnAlterarFoto = view.findViewById(R.id.btnAlterarFoto);
        btnAlterarSenha = view.findViewById(R.id.btnAlterarSenha);
        btnDeslogar = view.findViewById(R.id.btnDeslogar);
        user = view.findViewById(R.id.usuarioConfig);
        fotoPerfil = view.findViewById(R.id.profile_image);

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        fotoPerfilDrawer = headerView.findViewById(R.id.profile_image_drawer);
    }

    private void alterarSenha(String senha){
        dao = new UsuarioDAO(getContext());

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(preferences.getInt(KeysUtil.ID_USER_LOGIN, -1));
        usuario.setSenha(senha);

        dao.editarSenha(usuario);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECIONAR_IMAGEM) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                        byte[] imageBytes = imageUtil.imageToBytes(bitmap);

                        salvarImagemNoSQLite(imageBytes);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), getString(R.string.erroImagem), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), getString(R.string.imagemCancelada), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void salvarImagemNoSQLite(byte[] imageBytes) {
        usuario.setImagem(imageBytes);
        dao.editarImagem(usuario);

        Bitmap bitmap = imageUtil.bytesToImage(imageBytes);

        fotoPerfil.setImageBitmap(bitmap);
        fotoPerfilDrawer.setImageBitmap(bitmap);
    }

}