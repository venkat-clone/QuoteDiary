package com.android.quotediary.Reterofit.API;

import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginAPI {

    @POST("/auth/login")
    Call<UserModel> Login(@Body UserModel.Login userModel);
    @POST("/auth/register")
    Call<UserModel> SignUp(@Body UserModel.Register userModel);
    @GET("auth//user/dairyscount")
    Call<ResponseBody> getDairyCount(@Header("Authorization") String auth);
    @GET("/auth/user/dairys")
    Call<List<Dairy.ServerDairy>> getDairys(@Header("Authorization") String getAuthToken);
}
