package com.example.vento30.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vento30.CreateEventActivity;
import com.example.vento30.EventAPI;
import com.example.vento30.LogInActivity;
import com.example.vento30.R;
import com.example.vento30.databinding.FragmentHomeBinding;
import com.example.vento30.databinding.FragmentLogoutBinding;
import com.example.vento30.ui.events_search.HomeViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        startActivity(new Intent(getActivity(), LogInActivity.class));
        getActivity().finish();

        return root;
    }
}
