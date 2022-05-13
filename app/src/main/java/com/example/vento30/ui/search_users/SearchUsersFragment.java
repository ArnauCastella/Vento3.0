package com.example.vento30.ui.search_users;

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

import com.example.vento30.databinding.FragmentHomeBinding;
import com.example.vento30.databinding.FragmentSearchUsersBinding;
import com.example.vento30.ui.my_timeline.MyTimelineViewModel;

public class SearchUsersFragment extends Fragment {

    private SearchUsersViewModel searchUsersViewModel;
    private FragmentSearchUsersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        searchUsersViewModel =
                new ViewModelProvider(this).get(SearchUsersViewModel.class);

        binding = FragmentSearchUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSearchUsers;
        searchUsersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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