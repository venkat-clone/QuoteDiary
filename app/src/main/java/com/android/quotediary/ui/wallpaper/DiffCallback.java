package com.android.quotediary.ui.wallpaper;

import androidx.recyclerview.widget.DiffUtil;

import com.android.quotediary.models.DataModelOther;

import java.util.List;

public class DiffCallback extends DiffUtil.Callback {

    List<DataModelOther.Wallpaper> NewList;
    List<DataModelOther.Wallpaper> OldList;

    public DiffCallback(List<DataModelOther.Wallpaper> newList, List<DataModelOther.Wallpaper> oldList) {
        NewList = newList;
        OldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return OldList.size();
    }

    @Override
    public int getNewListSize() {
        return NewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return NewList.get(newItemPosition)==OldList.get(oldItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }
}
