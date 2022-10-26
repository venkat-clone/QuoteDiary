package com.android.quotediary.ui.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.quotediary.databinding.FragmentHomeBinding;
import com.android.quotediary.models.Dairy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.drawable.DrawableUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                    progressDialog.setMessage("Dairy is Updating ...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    mViewModel.updateDairy(requireContext(),dairy);
                    changeWallpaper(dairy.getContent());
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

    private void changeWallpaper(String text){
        String emoVar = Emotion.getEmotion(text);
        new Handler().postDelayed(()->{
            final WallpaperManager wallpaperManager = WallpaperManager.getInstance(requireContext());
            Glide.with(requireContext())
                    .load(Emotion.getWallpaper(emoVar))
                            .addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    try {
                                        wallpaperManager.setBitmap(((BitmapDrawable)resource).getBitmap());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return false;
                                }
                            }).submit();

        },5000);
    }
    static final class Emotion{
        static Emotion Happy = new Emotion(new String[]{"happy","excited", "joyful", "satisfied", "enjoyed"});
        static Emotion Sad = new Emotion(new String[]{"dipresed", "disapinted", "distrubed", "upset","lounly","sad","alone"});
        static Emotion angry = new Emotion(new String[]{"irritated", "bitter", "annoyed", "provoked","angry","irritate"});
        static Emotion fear = new Emotion(new String[]{"fear","anxiety", "despair", "scare", "panic","scared"});
        static Emotion surprise = new Emotion(new String[]{"surprise","curiosity", "mirackle", "shock", "wonder"});
        String[] values;
        Emotion(String[] values){
            this.values = values;
        }
        static String getEmotion(String text){
            String[] textLink =  text.split(" ");
            int hCount = 0;
            int sCount = 0;
            int aCount = 0;
            int fCount = 0;
            int supCount = 0;
            for (String s : textLink) {
                hCount += Happy.getMatches(s);
                sCount += Sad.getMatches(s);
                aCount += angry.getMatches(s);
                fCount += fear.getMatches(s);
                supCount += surprise.getMatches(s);
            }
            int mx = Math.max(Math.max(Math.max(hCount,aCount),Math.max(sCount,fCount)),supCount);
            if(mx==hCount) return "happy";
            if(mx==sCount) return "sad";
            if(mx==aCount) return "angry";
            if(mx==fCount) return "fear";
            return "surprise";

        }
        private int getMatches(String s){
            int co=0;
            for (String value : values) {
                if (value.toLowerCase().equals(s.toLowerCase())) {
                    co++;
                }
            }
            return co;
        }

        static String[] hWallpaper = new String[]{
                "https://pixabay.com/get/g14d09e195ac9b98e1352bd694de6159e9d275859c6f2938b4e2af9bc1d05dca9e35952bc7feca78a9361f0f3caf68e92715f5e84296cda8136a4db23e76dd476_1280.jpg",
                "https://pixabay.com/get/g87f1a49eb59d837fe393016d76112e3dc617e678c51ac6951e61ab10b078c479a1fe78f2e493a1c759573dff62780bf22a630a629350a6179b1213188ee6064d_1280.jpg",
                "https://pixabay.com/get/g30dda0675dd5ec0cc83089fba54b1050232c888b4d9bd244328dfd32e0aebaa6ae78b9c2188c17072cde527f9e4efe9860328221e2011df3243e0860be0aaf75_1280.jpg"        };
        static String[] sWallpaper = new String[]{

                "https://pixabay.com/get/g14d09e195ac9b98e1352bd694de6159e9d275859c6f2938b4e2af9bc1d05dca9e35952bc7feca78a9361f0f3caf68e92715f5e84296cda8136a4db23e76dd476_1280.jpg",
                "https://pixabay.com/get/g87f1a49eb59d837fe393016d76112e3dc617e678c51ac6951e61ab10b078c479a1fe78f2e493a1c759573dff62780bf22a630a629350a6179b1213188ee6064d_1280.jpg",
                "https://pixabay.com/get/g30dda0675dd5ec0cc83089fba54b1050232c888b4d9bd244328dfd32e0aebaa6ae78b9c2188c17072cde527f9e4efe9860328221e2011df3243e0860be0aaf75_1280.jpg"
        };
        static String[] aWallpaper = new String[]{
                "https://pixabay.com/get/g14d09e195ac9b98e1352bd694de6159e9d275859c6f2938b4e2af9bc1d05dca9e35952bc7feca78a9361f0f3caf68e92715f5e84296cda8136a4db23e76dd476_1280.jpg",
                "https://pixabay.com/get/g87f1a49eb59d837fe393016d76112e3dc617e678c51ac6951e61ab10b078c479a1fe78f2e493a1c759573dff62780bf22a630a629350a6179b1213188ee6064d_1280.jpg",
                "https://pixabay.com/get/g30dda0675dd5ec0cc83089fba54b1050232c888b4d9bd244328dfd32e0aebaa6ae78b9c2188c17072cde527f9e4efe9860328221e2011df3243e0860be0aaf75_1280.jpg"
        };
        static String[] fWallpaper = new String[]{
                "https://pixabay.com/get/g14d09e195ac9b98e1352bd694de6159e9d275859c6f2938b4e2af9bc1d05dca9e35952bc7feca78a9361f0f3caf68e92715f5e84296cda8136a4db23e76dd476_1280.jpg",
                "https://pixabay.com/get/g87f1a49eb59d837fe393016d76112e3dc617e678c51ac6951e61ab10b078c479a1fe78f2e493a1c759573dff62780bf22a630a629350a6179b1213188ee6064d_1280.jpg",
                "https://pixabay.com/get/g30dda0675dd5ec0cc83089fba54b1050232c888b4d9bd244328dfd32e0aebaa6ae78b9c2188c17072cde527f9e4efe9860328221e2011df3243e0860be0aaf75_1280.jpg"
        };
        static String[] supWallpaper = new String[]{
                "https://pixabay.com/get/g14d09e195ac9b98e1352bd694de6159e9d275859c6f2938b4e2af9bc1d05dca9e35952bc7feca78a9361f0f3caf68e92715f5e84296cda8136a4db23e76dd476_1280.jpg",
                "https://pixabay.com/get/g87f1a49eb59d837fe393016d76112e3dc617e678c51ac6951e61ab10b078c479a1fe78f2e493a1c759573dff62780bf22a630a629350a6179b1213188ee6064d_1280.jpg",
                "https://pixabay.com/get/g30dda0675dd5ec0cc83089fba54b1050232c888b4d9bd244328dfd32e0aebaa6ae78b9c2188c17072cde527f9e4efe9860328221e2011df3243e0860be0aaf75_1280.jpg"
        };

        static String getWallpaper(String s){
            switch (s){
                case "happy":
                    return hWallpaper[((int)System.currentTimeMillis()%3)];
                case "sad":
                    return sWallpaper[((int)System.currentTimeMillis()%3)];
                case "angry":
                    return aWallpaper[((int)System.currentTimeMillis()%3)];
                case "fear":
                    return fWallpaper[((int)System.currentTimeMillis()%3)];
                case "surprise":
                    return supWallpaper[((int)System.currentTimeMillis()%3)];

            }
            return "";
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}