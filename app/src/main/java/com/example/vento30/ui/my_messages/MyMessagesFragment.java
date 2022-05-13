package  com.example.vento30.ui.my_messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import  com.example.vento30.databinding.FragmentMyMessagesBinding;

public class MyMessagesFragment extends Fragment {

    private MyMessagesViewModel myMessagesViewModel;
    private FragmentMyMessagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        myMessagesViewModel =
                new ViewModelProvider(this).get(MyMessagesViewModel.class);

        binding = FragmentMyMessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMyMessages;
        myMessagesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}