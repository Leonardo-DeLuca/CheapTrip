package activities.fragments.configuracoes;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import activities.LoginActivity;
import database.dao.UsuarioDAO;
import database.model.UsuarioModel;
import de.hdodenhof.circleimageview.CircleImageView;
import util.KeysUtil;

public class ConfiguracoesFragment extends Fragment {
    private FragmentConfiguracoesBinding binding;

    private SharedPreferences preferences;

    private Button btnAlterarFoto;
    private TextView user;
    private Button btnAlterarSenha;
    private Button btnDeslogar;
    private UsuarioDAO dao;
    private TextInputEditText senha;
    private TextInputEditText repeteSenha;
    private EditText alterarSenha;
    private CircleImageView fotoPerfil;

    private static final int PICK_IMAGE = 1;

    private boolean validado = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfiguracoesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        preferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        setarIds(view);

        user.setText(preferences.getString(KeysUtil.USER_LOGADO, "user"));

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
                                    senha.setError("Campo senha obrigatório!");
                                    Toast.makeText(getContext(), R.string.toastSenhaEmpty, Toast.LENGTH_LONG).show();
                                    senha.setEnabled(true);
                                } else if (!senha.getText().toString().equals(repeteSenha.getText().toString())) {
                                    repeteSenha.requestFocus();
                                    Toast.makeText(getContext(), R.string.toastSenhasDiferentes, Toast.LENGTH_LONG).show();
                                } else {
                                    senhaNova = senha.getText().toString();
                                    alterarSenha(senhaNova, view);
                                    Toast.makeText(view.getContext(), R.string.toastSenhaAlterada, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }
            });
        });

        btnAlterarFoto.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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
    }

    private void alterarSenha(String senha, View view){
        dao = new UsuarioDAO(getContext());

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(preferences.getInt(KeysUtil.ID_USER_LOGIN, -1));
        usuario.setSenha(senha);
        dao.editarSenha(usuario);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == getActivity().RESULT_OK) {
                if (data != null) {
                    try {
                        // Obter o bitmap da imagem selecionada
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                        // Converte o bitmap em um array de bytes
                        byte[] imageBytes = imageToBytes(bitmap);

                        // Salva a imagem no banco de dados SQLite
                        saveImageToDatabase(imageBytes);

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] imageToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void saveImageToDatabase(byte[] imageBytes) {
//        dao = new UsuarioDAO(getContext());
//
//        UsuarioModel usuario = new UsuarioModel();
//        usuario.setId(preferences.getInt(KeysUtil.ID_USER_LOGIN, -1));
//        usuario.setImagem(imageBytes);
//        dao.editarImagem(usuario);

//        Bitmap imageFromBytes = bytesToImage(usuario.getImagem());
//        preferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
//        SharedPreferences.Editor edit = preferences.edit();

//        fotoPerfil.setImageBitmap(imageFromBytes);

    }

    public Bitmap bytesToImage(byte[] imageBytes) {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
}

