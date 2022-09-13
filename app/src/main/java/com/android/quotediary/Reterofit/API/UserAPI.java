package com.android.quotediary.Reterofit.API;

import android.hardware.lights.LightState;

import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {


    @GET("/auth/wallpapers")
    Call<List<DataModelOther.Wallpaper>> getWallpapers(@Header("Authorization") String auth,@Query("page") String page);

    @GET("/auth/search/wall")
    Call<List<DataModelOther.Wallpaper>> searchWallpapers(@Header("Authorization") String auth,@Query("page") String page,@Query("pix") String query);

    @GET("/auth/quotes")
    Call<List<DataModelOther.Quote>> getQuotes(@Header("Authorization") String auth,@Query("page") String page);

    @POST("/auth/createdairy")
    Call<Dairy.ServerDairy> postDairy(@Header("Authorization") String auth,@Body Dairy.ServerDairy serverDairy);
    @POST("/auth/updatedairy")
    Call<Dairy.ServerDairy> updateDairy(@Header("Authorization") String auth,@Body Dairy.ServerDairy serverDairy);

    @POST("/auth/refresh-token")
    Call<UserModel> RefreshToken(@Body UserModel getAuthToken);

}
