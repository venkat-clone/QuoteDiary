package com.android.quotediary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.models.UserModel;

public class LoginActivityViewModel extends ViewModel {
    public MutableLiveData<String> Email;
    public MutableLiveData<String> Password;
    public MutableLiveData<Integer> ErrorCode;
    public MutableLiveData<String> ErrorMessage;
    public MutableLiveData<UserModel> usermodel;
    public MutableLiveData<Integer> loginresponce;

    public LoginActivityViewModel() {
        Email= new MutableLiveData<>();
        Password = new MutableLiveData<>();
        ErrorCode = new MutableLiveData<>();
        ErrorMessage = new MutableLiveData<>("");
        usermodel = new MutableLiveData<>();
        loginresponce = new MutableLiveData<>();
    }

    public MutableLiveData<String> getPassword() {
        return Password;
    }

    public void setPassword(MutableLiveData<String> password) {
        Password = password;
    }

    public MutableLiveData<Integer> getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(MutableLiveData<Integer> errorCode) {
        ErrorCode = errorCode;
    }

    public MutableLiveData<String> getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getEmail() {
        return Email.getValue();
    }

    public void setEmail(String email) {
        Email.setValue(email);
    }
}
