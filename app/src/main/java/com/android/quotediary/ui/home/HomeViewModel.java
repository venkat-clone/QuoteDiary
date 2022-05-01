package com.android.quotediary.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.DairyYear;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<CardViewAdapter.ViewHolder> Expanded;
    public MutableLiveData<DairyYear> selectedYear;
    public MutableLiveData<YearAdapter.ViewHolder> selectedHolder;
    MutableLiveData<Dairy.My_Date> today;



    public HomeViewModel() {
        mText = new MutableLiveData<>("This is Home Fragment");
        Expanded = new MutableLiveData<>();
        selectedYear = new MutableLiveData<>();
        selectedHolder = new MutableLiveData<>();
        today = new MutableLiveData<>(Dairy.My_Date.getToday());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }

    public MutableLiveData<CardViewAdapter.ViewHolder> getExpanded() {
        return Expanded;
    }

    public void setExpanded(MutableLiveData<CardViewAdapter.ViewHolder> expanded) {
        Expanded = expanded;
    }
    public MutableLiveData<Dairy.My_Date> getToday() {
        return today;
    }

    public void setToday(MutableLiveData<Dairy.My_Date> today) {
        this.today = today;
    }
}