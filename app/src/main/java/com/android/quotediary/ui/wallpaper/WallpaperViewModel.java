package com.android.quotediary.ui.wallpaper;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.models.DataModelOther;

public class WallpaperViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<Boolean> isScrolling;
    MutableLiveData<DataModelOther.Wallpaper> selectedWall;



    public WallpaperViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
        selectedWall = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Boolean> getIsScrolling() {
        return isScrolling;
    }

    public void setIsScrolling(MutableLiveData<Boolean> issrolling) {
        this.isScrolling = issrolling;
    }
    public void LodeMore(View view){
        // Load More
    }

    public MutableLiveData<DataModelOther.Wallpaper> getSelectedWall() {
        return selectedWall;
    }

    public void setSelectedWall(MutableLiveData<DataModelOther.Wallpaper> selectedWall) {
        this.selectedWall = selectedWall;
    }
}