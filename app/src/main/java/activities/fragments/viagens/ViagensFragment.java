package activities.fragments.viagens;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cheaptrip.R;
import com.example.cheaptrip.databinding.FragmentViagensBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import activities.CadastroViagensActivity;

public class ViagensFragment extends Fragment {

    private FragmentViagensBinding binding;
    private FloatingActionButton fab;
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViagensBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();

        TextView textView = view.findViewById(R.id.text_home);
        textView.setText("This is teste fragment");

        FloatingActionButton btnAdd = view.findViewById(R.id.btn_add_viagem);
        btnAdd.setOnClickListener(view1 -> {
            Intent i = new Intent(getContext(), CadastroViagensActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}