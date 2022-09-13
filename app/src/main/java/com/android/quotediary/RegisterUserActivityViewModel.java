package com.android.quotediary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.models.UserModel;

public class RegisterUserActivityViewModel extends ViewModel {

    MutableLiveData<Integer> Signinresponce = new MutableLiveData<>();;



    MutableLiveData<UserModel.Register> userModel = new MutableLiveData<>(new UserModel.Register());;

    public void init(){
        Signinresponce = new MutableLiveData<>();
        userModel = new MutableLiveData<>(new UserModel.Register());
    }
    public MutableLiveData<Integer> getSigninresponce() {
        return Signinresponce;
    }

    public void setSigninresponce(MutableLiveData<Integer> signinresponce) {
        Signinresponce = signinresponce;
    }

    public MutableLiveData<UserModel.Register> getUserModel() {
        return userModel;
    }

    public void setUserModel(MutableLiveData<UserModel.Register> userModel) {
        this.userModel = userModel;
    }
}
