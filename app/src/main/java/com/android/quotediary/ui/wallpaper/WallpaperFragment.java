package com.android.quotediary.ui.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.quotediary.databinding.FragmentWallpaperBinding;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.ui.WallPaperActivity;

import java.util.ArrayList;
import java.util.List;

public class WallpaperFragment extends Fragment {

    private FragmentWallpaperBinding binding;
    WallpaperViewModel mViewModel;
    int Current,Total,ScrolledOut;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(WallpaperViewModel.class);
        binding = FragmentWallpaperBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        List<DataModelOther.Wallpaper> wallpapers = new ArrayList<>();
        wallpapers.add(new DataModelOther.Wallpaper("https://media.istockphoto.com/photos/abstract-digitally-generated-background-image-picture-id1314544146",640,740));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",630,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",640,840));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",640,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",640,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",940,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",680,1040));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",640,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",640,540));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",640,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",640,670));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",540,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",640,630));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",670,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",640,740));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",630,780));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",640,730));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",740,760));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",780,780));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",730,800));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",760,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",780,940));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",800,740));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",640,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",940,740));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",740,780));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",680,730));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",940,760));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",660,780));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",690,800));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",640,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",780,780));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",730,800));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",760,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",780,940));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",800,740));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",640,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",780,780));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1569887652102-59696fef1528",730,800));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568826069038-f3de1448e9a1",760,640));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568160277762-0224a391b5a5",780,940));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c",800,740));
        wallpapers.add(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568158951631-e1f33137ad76",640,640));

        WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(getContext(),mViewModel,wallpapers);
        binding.recyclerWallpaper.setAdapter(wallpaperAdapter);

        Observers();

        return binding.getRoot();
    }

    void Observers(){
        mViewModel.getSelectedWall().observe(getViewLifecycleOwner(), new Observer<DataModelOther.Wallpaper>() {
            @Override
            public void onChanged(DataModelOther.Wallpaper wallpaper) {
                if(wallpaper!=null){
                    Intent intent = new Intent(getContext(), WallPaperActivity.class);
                    intent.putExtra("wallpaper",wallpaper.getUrl());
                    startActivity(intent);
                    mViewModel.getSelectedWall().setValue(null);
                }
            }
        });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}