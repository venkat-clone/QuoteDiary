package com.android.quotediary.ui.quotes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.models.DataModelOther;

public class QuotesViewModel extends ViewModel {
    MutableLiveData<DataModelOther.FontStyle> selected;

    public QuotesViewModel() {
        selected = new MutableLiveData<>();
    }

}