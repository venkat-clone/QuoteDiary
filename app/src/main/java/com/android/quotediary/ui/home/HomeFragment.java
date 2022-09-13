package com.android.quotediary.ui.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.quotediary.databinding.FragmentHomeBinding;
import com.android.quotediary.models.Dairy;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel mViewModel;
    CardViewAdapter cardViewAdapter;
    YearAdapter yearAdapter;
    ProgressDialog progressDialog;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    public HomeFragment(){

    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
//        binding.setModel(mViewModel);
        mViewModel.SetupDb(getContext().getApplicationContext());

        init();
        Observers();
        return binding.getRoot();
    }

    public void init(){
        yearAdapter = new YearAdapter(getContext(),mViewModel,new ArrayList<>());
        binding.recyclerHo.setAdapter(yearAdapter);
        cardViewAdapter = new CardViewAdapter(new ArrayList<>(),getContext(), mViewModel);
        binding.recycler.setAdapter(cardViewAdapter);
        mViewModel.getDairyList();
//        mViewModel.insertYear();
    }


    public void Observers(){
        mViewModel.selectedYear.observe(getViewLifecycleOwner(), new Observer<Dairy.Year>() {
            @Override
            public void onChanged(Dairy.Year dairyYear) {
                if(dairyYear!=null ) {
                    yearAdapter.Merge();
                    cardViewAdapter.Update(dairyYear.getDairyS());
                }
            }
        });
        mViewModel.getYearsFromDb().observe(getViewLifecycleOwner(), new Observer<List<Dairy.Year>>() {
            @Override
            public void onChanged(List<Dairy.Year> years) {
                if(binding.refreshLayout.isRefreshing()) binding.refreshLayout.setRefreshing(false);
                    if(years!=null){
                    yearAdapter.Update(years);
                    mViewModel.selectedYear.postValue(years.get(years.size()-1));
                    mViewModel.oldPos = years.size()-1;
                    mViewModel.newPos = mViewModel.oldPos;
                }
            }
        });
//        mViewModel.selectedDairy.observe(getViewLifecycleOwner(), new Observer<Dairy>() {
//            @Override
//            public void onChanged(Dairy dairy) {
//                cardViewAdapter.Merge();
//            }
//        });
        mViewModel.uploadToday.observe(getViewLifecycleOwner(), new Observer<Dairy.ServerDairy>() {
            @Override
            public void onChanged(Dairy.ServerDairy dairy) {
                if(dairy!=null){
                    progressDialog = new ProgressDialog(requireActivity());
                    progressDialog.setMessage("Dairy is Uploading...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    mViewModel.updateDairy(requireContext(),dairy);
                    mViewModel.uploadToday.setValue(null);
                }
            }
        });
        mViewModel.serverDairy.observe(getViewLifecycleOwner(), new Observer<Dairy.ServerDairy>() {
            @Override
            public void onChanged(Dairy.ServerDairy serverDairy) {
                if(serverDairy!=null) {
                    mViewModel.updateLoacaly(serverDairy);
                    mViewModel.serverDairy.postValue(null);
//                    mViewModel.getDairyList();
                }

            }
        });
        mViewModel.isSucessfull.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    mViewModel.getDairyList();
                    mViewModel.isSucessfull.postValue(false);
                    if(progressDialog!=null) progressDialog.cancel();
                }
            }
        });

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getDairyList();
            }
        });



//        mViewModel.getDairyListLiveData().observe(getViewLifecycleOwner(), new Observer<List<DairyEntity>>() {
//            @Override
//            public void onChanged(List<DairyEntity> dairyEntities) {
//                if(dairyEntities!=null){
//                    if(dairyEntities.size()==0){
//                        DairyEntity dairyEntity = new DairyEntity();
//                        Calendar calendar = Calendar.getInstance();
//                        dairyEntity.setDay(calendar.get(Calendar.DAY_OF_YEAR));
//                        dairyEntity.setContent("MY DAIRY MY MOOD");
//                        dairyEntity.setYear(calendar.get(Calendar.YEAR));
//                        dairyEntity.setKey("non");
//                        mViewModel.insert(dairyEntity);
//                    }
//                    else {
//                        DairyEntity dairyEntity = dairyEntities.get(0);
//                        Dairy dairy = new Dairy();
//                        dairy.setContent(dairyEntity.getContent());
//                        Dairy.My_Date date = new Dairy.My_Date(dairyEntity.getYear(),dairyEntity.getDay());
//                        dairy.setDate(date);
//                        DairyYear year = new DairyYear();
//                        year.setYear(String.valueOf(dairyEntity.getYear()));
//                        year.setDaries(new ArrayList<>());
//                        year.getDaries().add(dairy);
//                        List<DairyYear> list = new ArrayList<>();
//                        list.add(year);
//                        yearAdapter.Update(list);
//
//                    }
//                    mViewModel.getDairyList();
//                }
//                else {
//                    dairyEntities.size();
//                }
//            }
//        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}