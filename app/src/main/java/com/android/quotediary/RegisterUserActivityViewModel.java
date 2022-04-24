package com.android.quotediary;

import androidx.lifecycle.MutableLiveData;

public class RegisterUserActivityViewModel {

    MutableLiveData<Integer> Signinresponce;

    RegisterUserActivityViewModel(){
        init();
    }
    public void init(){
        Signinresponce = new MutableLiveData<>();
    }

}
