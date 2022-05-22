package com.android.quotediary.Reterofit.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.quotediary.Reterofit.API.UserAPI;
import com.android.quotediary.Reterofit.ReterofirServices;
import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.sharedPreferenceServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {

    Context context;
    UserAPI userAPI;
    public UserRepository(Context context) {
        this.context = context;
        Retrofit reterofirServices = ReterofirServices.CreateReterofitServicers();
        Log.i("API",reterofirServices.baseUrl()+"");
        userAPI = reterofirServices.create(UserAPI.class);
        this.context =context;
    }
    public UserRepository() {
        Retrofit reterofirServices = ReterofirServices.CreateReterofitServicers();
        Log.i("API",reterofirServices.baseUrl()+"");
        userAPI = reterofirServices.create(UserAPI.class);
     }

    public void getWallpapers(MutableLiveData<List<DataModelOther.Wallpaper>> wallpapers,int Page){
        userAPI.getWallpapers(sharedPreferenceServices.GetAuthToken(context),String.valueOf(Page)).enqueue(new Callback<List<DataModelOther.Wallpaper>>() {
            @Override
            public void onResponse(@NonNull Call<List<DataModelOther.Wallpaper>> call, @NonNull Response<List<DataModelOther.Wallpaper>> response) {
                if(response.isSuccessful()){
//                    if(wallpapers.getValue()!=null)
//                        response.body().addAll(wallpapers.getValue());
                    wallpapers.setValue(response.body());
                }
                else
                    Toast.makeText(context,"Something went wrong:",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<DataModelOther.Wallpaper>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(context,""+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchWallpapers(MutableLiveData<List<DataModelOther.Wallpaper>> searchResults,int Page,String Query){
        userAPI.searchWallpapers(sharedPreferenceServices.GetAuthToken(context),String.valueOf(Page),Query)
                .enqueue(new Callback<List<DataModelOther.Wallpaper>>() {
                    @Override
                    public void onResponse(Call<List<DataModelOther.Wallpaper>> call, Response<List<DataModelOther.Wallpaper>> response) {
                        if(response.isSuccessful())
                            searchResults.postValue(response.body());
                        else
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<List<DataModelOther.Wallpaper>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context,""+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void getQuotes(Context context,MutableLiveData<List<DataModelOther.Quote>> livelist,int Page){
        userAPI.getQuotes(sharedPreferenceServices.GetAuthToken(context),String.valueOf(Page))
                .enqueue(new Callback<List<DataModelOther.Quote>>() {
                    @Override
                    public void onResponse(Call<List<DataModelOther.Quote>> call, Response<List<DataModelOther.Quote>> response) {
                        if(response.isSuccessful()){
                            livelist.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DataModelOther.Quote>> call, Throwable t) {
                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void update(Context context,Dairy.ServerDairy value, MutableLiveData<Dairy.ServerDairy> dbResponse) {
        if(value.unsaved){
            // Create Route
            Dairy.ServerDairy dairy = new Dairy.ServerDairy(value.getContent(),value.getYear(),value.getDay());
            userAPI.postDairy(sharedPreferenceServices.GetAuthToken(context),dairy)
                    .enqueue(new Callback<Dairy.ServerDairy>() {
                        @Override
                        public void onResponse(Call<Dairy.ServerDairy> call, Response<Dairy.ServerDairy> response) {
                            if(response.isSuccessful()){
                                response.body().unsaved =true;
                                dbResponse.setValue(response.body());

                            }
                        }

                        @Override
                        public void onFailure(Call<Dairy.ServerDairy> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
        else {
            Dairy.ServerDairy dairy = new Dairy.ServerDairy(value.getId(),value.getContent(),value.getYear(),value.getDay());

            userAPI.updateDairy(sharedPreferenceServices.GetAuthToken(context),dairy).enqueue(new Callback<Dairy.ServerDairy>() {
                @Override
                public void onResponse(Call<Dairy.ServerDairy> call, Response<Dairy.ServerDairy> response) {
                    if(response.isSuccessful()){
                        if(response.isSuccessful()){
                            dbResponse.setValue(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Dairy.ServerDairy> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        }
    }
}
