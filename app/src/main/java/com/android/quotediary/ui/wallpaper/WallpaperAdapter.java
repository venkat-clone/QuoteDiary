package com.android.quotediary.ui.wallpaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.quotediary.R;
import com.android.quotediary.databinding.WallpaperListItemBinding;
import com.android.quotediary.models.DataModelOther;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {
    Context context;
    WallpaperViewModel wallpaperViewModel;
    List<DataModelOther.Wallpaper> wallpapers;
    public WallpaperAdapter(Context context, WallpaperViewModel wallpaperViewModel,List<DataModelOther.Wallpaper> wallpapers) {
        this.context = context;
        this.wallpaperViewModel =wallpaperViewModel;
        this.wallpapers = wallpapers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wallpaper_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setModel(wallpapers.get(position));
        holder.binding.setClickHandler(new ClickHandler());
    }
    public class ClickHandler{
        public void onClicked(View view, DataModelOther.Wallpaper model){
            wallpaperViewModel.getSelectedWall().setValue(model);
        }
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WallpaperListItemBinding binding;
        public ViewHolder(@NonNull WallpaperListItemBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
