package com.android.quotediary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.UserModel;

import java.util.List;

public class LoginActivityViewModel extends ViewModel {
    public MutableLiveData<String> Email;
    public MutableLiveData<String> Password;
    public MutableLiveData<Integer> ErrorCode;
    public MutableLiveData<String> ErrorMessage;
    public MutableLiveData<UserModel> usermodel;

    public MutableLiveData<Integer> DownloadResponce;
    public MutableLiveData<List<Dairy.ServerDairy>> DairyList;

    public LoginActivityViewModel() {
        DairyList = new MutableLiveData<>();
        Email= new MutableLiveData<>();
        Password = new MutableLiveData<>();
        ErrorCode = new MutableLiveData<>();
        ErrorMessage = new MutableLiveData<>("");
        usermodel = new MutableLiveData<>();

        DownloadResponce = new MutableLiveData<>();
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

    public MutableLiveData<Integer> getDownloadResponce() {
        return DownloadResponce;
    }

    public void setDownloadResponce(MutableLiveData<Integer> downloadResponce) {
        DownloadResponce = downloadResponce;
    }
}
