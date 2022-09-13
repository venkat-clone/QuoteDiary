package com.android.quotediary.ui.wallpaper;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.android.quotediary.R;
import com.android.quotediary.databinding.FragmentWallpaperBinding;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.ui.WallPaperActivity;

import java.util.ArrayList;
import java.util.List;

public class WallpaperFragment extends Fragment {

    private FragmentWallpaperBinding binding;
    WallpaperViewModel mViewModel;
    WallpaperAdapter wallpaperAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(WallpaperViewModel.class);
        binding = FragmentWallpaperBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        wallpaperAdapter = new WallpaperAdapter(getContext(),mViewModel,new ArrayList<>());
        binding.recyclerWallpaper.setAdapter(wallpaperAdapter);


        mViewModel.CreateRepo(requireContext());
        Observers();
        binding.setClickHandler(new ClickHandler());
        return binding.getRoot();
    }

    void Observers(){
        binding.swipelayout.setOnRefreshListener(() -> {
            mViewModel.page =0;
            if(mViewModel.query.getValue()==null ){
                mViewModel.LoadMore();
            }
            else mViewModel.saerchWall();
            wallpaperAdapter.initialize();
            mViewModel.isLoading.setValue(true);
            binding.swipelayout.setRefreshing(false);
        });
        binding.recyclerWallpaper.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) binding.recyclerWallpaper.getLayoutManager();
                if(gridLayoutManager.findLastVisibleItemPosition() == wallpaperAdapter.getItemCount()-1) {
                    if(mViewModel.query.getValue()==null ){
                        mViewModel.LoadMore();
                    }
                    else mViewModel.saerchWall();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        binding.searchLayout.setOnCloseListener(() -> {
            mViewModel.page =0;
            mViewModel.query = new MutableLiveData<>();
            mViewModel.LoadMore();
            return false;
        });
        binding.searchLayout.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.searchLayout.getWindowToken(), 0);
                binding.searchLayout.clearFocus();
                mViewModel.query.setValue(query);
                mViewModel.isLoading.setValue(true);
                mViewModel.page =0;
                mViewModel.saerchWall();
                wallpaperAdapter.initialize();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) wallpaperAdapter.initialize();
                return true;
            }
        });

        mViewModel.wallpapers.observe(getViewLifecycleOwner(), wallpapers -> {
            binding.swipelayout.setRefreshing(false);
            if(wallpapers!=null && wallpapers.size()!=0){
                wallpaperAdapter.update(wallpapers);
            }
            else if(wallpapers!=null){
                mViewModel.page =-1;
            }
            mViewModel.isLoading.setValue(false);
        });

        mViewModel.getSelectedWall().observe(getViewLifecycleOwner(), wallpaper -> {
            if(wallpaper!=null){
                Intent intent = new Intent(getContext(), WallPaperActivity.class);
                intent.putExtra("wallpaper",wallpaper.getUrl());
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                mViewModel.getSelectedWall().setValue(null);
            }
        });


    }
    public class ClickHandler{
        public void LoadMore(View view){
            mViewModel.LoadMore();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}