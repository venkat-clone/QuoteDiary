package com.android.quotediary.ui.wallpaper;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.android.quotediary.Reterofit.Repository.UserRepository;
import com.android.quotediary.models.DataModelOther;

import java.util.List;

public class WallpaperViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<Boolean> isScrolling;
    MutableLiveData<DataModelOther.Wallpaper> selectedWall;
    MutableLiveData<List<DataModelOther.Wallpaper>> wallpapers;
    MutableLiveData<String> query;
    UserRepository userRepository;

    int page=0;
//    MutableLiveData<>


    public WallpaperViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
        query = new MutableLiveData<>();
        selectedWall = new MutableLiveData<>();
        wallpapers = new MutableLiveData<>();
    }
    public void CreateRepo(Context context){
        wallpapers = new MutableLiveData<>();
        userRepository = new UserRepository(context);
        userRepository.getWallpapers(wallpapers,++page);

    }
    public void LoadMore(){
        if(page >=0) userRepository.getWallpapers(wallpapers,++page);

    }
    public void saerchWall(){
        if(page >=0) userRepository.searchWallpapers(wallpapers,++page,query.getValue());
    }


//    public LiveData<String> getText() {
//        return mText;
//    }

    public void search(){

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