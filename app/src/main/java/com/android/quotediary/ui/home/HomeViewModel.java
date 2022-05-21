package com.android.quotediary.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.Helpers.Room.DairyEntity;
import com.android.quotediary.Helpers.Room.DairyRepository;
import com.android.quotediary.Reterofit.Repository.UserRepository;
import com.android.quotediary.models.Dairy;

import java.util.List;

public class HomeViewModel extends ViewModel {

    public int newPos=0;
    public MutableLiveData<Dairy.ServerDairy> uploadToday;
//    private MutableLiveData<CardViewAdapter.ViewHolder> Expanded;
    public MutableLiveData<Dairy.Year> selectedYear;
    public MutableLiveData<Boolean> isSucessfull;
    public int oldPos=0;
    MutableLiveData<List<Dairy.Year>> YearsFromDb;
    MutableLiveData<Dairy.ServerDairy> serverDairy;
    UserRepository userRepository ;


//    MutableLiveData<Dairy> selectedDairy;

    public MutableLiveData<YearAdapter.ViewHolder> selectedHolder;
//    MutableLiveData<Dairy.My_Date> today;
    LiveData<List<DairyEntity>> DairyListLiveData;
    DairyRepository repository;
    public HomeViewModel(){
//        super();
        init();
    }

    public void init() {
//        Expanded = new MutableLiveData<>();
        selectedYear = new MutableLiveData<>();
//        selectedHolder = new MutableLiveData<>();
        YearsFromDb = new MutableLiveData<>();
        isSucessfull = new MutableLiveData<>(false);
        userRepository = new UserRepository();
        serverDairy = new MutableLiveData<>();
//        today = new MutableLiveData<>(Dairy.My_Date.getToday());
//        selectedDairy = new MutableLiveData<>();
        uploadToday = new MutableLiveData<>();

    }
    public void getDairyList(){
//        DairyListLiveData = repository.getAll();
        long t1 = System.currentTimeMillis();
        repository.getYears(YearsFromDb);
        long t2 = System.currentTimeMillis();
        Log.i("Time",""+String.valueOf(t2 - t1));
    }
    public void SetupDb(Context context){
        repository = new DairyRepository(context);

    }

    public LiveData<List<DairyEntity>> getDairyListLiveData() {
        return DairyListLiveData;
    }
    public void insert(DairyEntity dairyEntity){
        repository.insert(dairyEntity);
    }
    public void insertYear(){
        repository.CleanDB();
//        repository.insert(new DairyEntity.Year(2002));
        long t1 = System.currentTimeMillis();
        for (int i = 2010; i < 2020; i++) {
            for (int j = 1; j <= 365; j++) {
                Dairy dairy = new Dairy(String.valueOf(i)+":"+String.valueOf(j),i,j);
                repository.insert(dairy);
            }
        }
        long t2 = System.currentTimeMillis();
        Log.i("Time",""+String.valueOf(t2 - t1));
    }

    public void setDairyListLiveData(LiveData<List<DairyEntity>> dairyListLiveData) {
        DairyListLiveData = dairyListLiveData;
    }

//    public MutableLiveData<CardViewAdapter.ViewHolder> getExpanded() {
//        return Expanded;
//    }
//
//    public void setExpanded(MutableLiveData<CardViewAdapter.ViewHolder> expanded) {
//        Expanded = expanded;
//    }

    public MutableLiveData<List<Dairy.Year>> getYearsFromDb() {
        return YearsFromDb;
    }

    public void setYearsFromDb(MutableLiveData<List<Dairy.Year>> yearsFromDb) {
        YearsFromDb = yearsFromDb;
    }

    //    public MutableLiveData<Dairy.My_Date> getToday() {
//        return today;
//    }
//
//    public void setToday(MutableLiveData<Dairy.My_Date> today) {
//        this.today = today;
//    }

//    public MutableLiveData<Dairy> getSelectedDairy() {
//        return selectedDairy;
//    }
//
//    public void setSelectedDairy(MutableLiveData<Dairy> selectedDairy) {
//        this.selectedDairy = selectedDairy;
//    }

    public void updateDairy(Context context,Dairy.ServerDairy dairy) {
//        repository.update(uploadToday.getValue(),DbResponse);
        userRepository.update(context,dairy,serverDairy);
    }
    public void updateLoacaly(Dairy.ServerDairy dairy){
        repository.update(dairy, isSucessfull);

    }
}