package com.android.quotediary.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.quotediary.databinding.FragmentHomeBinding;
import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.DairyYear;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel mViewModel;
    CardViewAdapter cardViewAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setModel(mViewModel);

        List<DairyYear> list = new ArrayList<>();
        List<Dairy> dairies = new ArrayList<>();
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());dairies.add(new Dairy());
        list.add(new DairyYear("2002",dairies));
        Dairy.My_Date date = new Dairy.My_Date();
        List<Dairy> dairies1 = new ArrayList<>();
        date.setDay_year(140);
        dairies1.add(new Dairy(date));dairies1.add(new Dairy(date));dairies1.add(new Dairy(date));
        dairies1.add(new Dairy(date));dairies1.add(new Dairy(date));dairies1.add(new Dairy(date));
        dairies1.add(new Dairy(date));dairies1.add(new Dairy(date));dairies1.add(new Dairy(date));
        list.add(new DairyYear("2003",dairies1));
        Dairy.My_Date date1 = new Dairy.My_Date();
        date1.setDay_year(156);
        List<Dairy> dairies2 = new ArrayList<>();

        dairies2.add(new Dairy(date1));dairies2.add(new Dairy(date1));dairies2.add(new Dairy(date1));
        dairies2.add(new Dairy(date1));dairies2.add(new Dairy(date1));dairies2.add(new Dairy(date1));
        dairies2.add(new Dairy(date1));dairies2.add(new Dairy(date1));dairies2.add(new Dairy(date1));

        list.add(new DairyYear("2004",dairies2));
        date.setDay_year(180);
        List<Dairy> dairies3 = new ArrayList<>();
        dairies3.add(new Dairy(date));dairies3.add(new Dairy(date));
        dairies3.add(new Dairy(date));dairies3.add(new Dairy(date));
        dairies3.add(new Dairy(date));dairies3.add(new Dairy(date));

        list.add(new DairyYear("2005",dairies3));


        YearAdapter yearAdapter = new YearAdapter(getContext(),mViewModel,list);
        binding.recyclerHo.setAdapter(yearAdapter);
        cardViewAdapter = new CardViewAdapter(dairies,getContext(), mViewModel);
        binding.recycler.setAdapter(cardViewAdapter);
        init();
        Observers();
        return binding.getRoot();
    }

    public void init(){

    }


    public void Observers(){
        mViewModel.selectedYear.observe(getViewLifecycleOwner(), new Observer<DairyYear>() {
            @Override
            public void onChanged(DairyYear dairyYear) {
                if(dairyYear!=null)
                    cardViewAdapter.Update(dairyYear.getDaries());
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}