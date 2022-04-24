package com.android.quotediary.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.quotediary.databinding.FragmentHomeBinding;
import com.android.quotediary.models.Dairy;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        List<Dairy> dairies = new ArrayList<>();
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        CardViewAdapter cardViewAdapter = new CardViewAdapter(dairies,getContext());
        binding.recycler.setAdapter(cardViewAdapter);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}