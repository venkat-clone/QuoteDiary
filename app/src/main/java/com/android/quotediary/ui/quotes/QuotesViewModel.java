package com.android.quotediary.ui.quotes;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.sharedPreferenceServices;

public class QuotesViewModel extends AndroidViewModel {
    MutableLiveData<DataModelOther.FontStyle> selected;
    MutableLiveData<String> selectedQuote;
    MutableLiveData<Float>  textSize;
    public QuotesViewModel(Application application) {
        super(application);
        selectedQuote = new MutableLiveData<>("");
        selected = new MutableLiveData<>(new DataModelOther.FontStyle());
        textSize = new MutableLiveData<Float>(32f);
    }

    public MutableLiveData<DataModelOther.FontStyle> getSelected() {
        return selected;
    }

    public void setSelected(MutableLiveData<DataModelOther.FontStyle> selected) {
        this.selected = selected;
    }

    public MutableLiveData<String> getSelectedQuote() {
        return selectedQuote;
    }

    public void setSelectedQuote(MutableLiveData<String> selectedQuote) {
        this.selectedQuote = selectedQuote;
    }
    public void setQuoteData(Context context){
        DataModelOther.finalQuote finalQuote = new DataModelOther.finalQuote();
        finalQuote.setQuote(selectedQuote.getValue());
        finalQuote.setTextSize(textSize.getValue());
        finalQuote.setStyleID(selected.getValue().getId());
        sharedPreferenceServices.putTextStyle(finalQuote,context);
    }
}