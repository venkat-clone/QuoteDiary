package com.android.quotediary.Reterofit.API;

import com.android.quotediary.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginAPI {

    @POST("/auth/login")
    Call<UserModel> Login(@Body UserModel.Login userModel);
    @POST("/auth/register")
    Call<UserModel> SignUp(@Body UserModel.Register userModel);

}
