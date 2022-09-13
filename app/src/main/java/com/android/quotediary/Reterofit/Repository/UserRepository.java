package com.android.quotediary.Reterofit.Repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.quotediary.Reterofit.API.UserAPI;
import com.android.quotediary.Reterofit.ReterofirServices;
import com.android.quotediary.SplashScreenActivity;
import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.models.UserModel;
import com.android.quotediary.sharedPreferenceServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

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


        getToken( s -> userAPI.getWallpapers(s.getToken(), String.valueOf(Page))
                                .enqueue(new Callback<List<DataModelOther.Wallpaper>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<DataModelOther.Wallpaper>> call, @NonNull Response<List<DataModelOther.Wallpaper>> response) {
                        if (response.isSuccessful()) {
//                    if(wallpapers.getValue()!=null)
//                        response.body().addAll(wallpapers.getValue());
                            wallpapers.setValue(response.body());
                        }
                        else
                            Toast.makeText(context, "Something went wrong:", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<DataModelOther.Wallpaper>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    public void searchWallpapers(MutableLiveData<List<DataModelOther.Wallpaper>> searchResults,int Page,String Query){
        getToken( s -> userAPI.searchWallpapers(s.getToken(),String.valueOf(Page),Query)
                        .enqueue(new Callback<List<DataModelOther.Wallpaper>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<DataModelOther.Wallpaper>> call, @NonNull Response<List<DataModelOther.Wallpaper>> response) {
                        if(response.isSuccessful())
                            searchResults.postValue(response.body());
                        else if (response.code()==401) getNewToken();

                        else
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<DataModelOther.Wallpaper>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context,""+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }


    public void getQuotes(Context context,MutableLiveData<List<DataModelOther.Quote>> liveList,int Page){
        getToken( s->userAPI.getQuotes(s.getToken(),String.valueOf(Page))
                        .enqueue(new Callback<List<DataModelOther.Quote>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<DataModelOther.Quote>> call, @NonNull Response<List<DataModelOther.Quote>> response) {
                                if(response.isSuccessful()){
                                    liveList.setValue(response.body());
                                }
                                else if (response.code()==401) getNewToken();
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<DataModelOther.Quote>> call, @NonNull Throwable t) {
                                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
                            }
                        }));

    }

    public void update(Context context,Dairy.ServerDairy value, MutableLiveData<Dairy.ServerDairy> dbResponse) {
        if(value.unsaved){
            // Create Route
            getToken( s->{Dairy.ServerDairy dairy = new Dairy.ServerDairy(value.getContent(),value.getYear(),value.getDay());
                        userAPI.postDairy(s.getToken(),dairy)
                                .enqueue(new Callback<Dairy.ServerDairy>() {
                                    @Override
                                    public void onResponse(@NonNull Call<Dairy.ServerDairy> call, @NonNull Response<Dairy.ServerDairy> response) {
                                        if(response.isSuccessful()){
                                            assert response.body() != null;
                                            response.body().unsaved =true;
                                            dbResponse.setValue(response.body());

                                        }
                                        else if (response.code()==401) getNewToken();
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<Dairy.ServerDairy> call, @NonNull Throwable t) {
                                        t.printStackTrace();
                                    }
                                });}
            );

        }
        else {
            getToken( s->{Dairy.ServerDairy dairy = new Dairy.ServerDairy(value.getId(),value.getContent(),value.getYear(),value.getDay());

                        userAPI.updateDairy(s.getToken(),dairy).enqueue(new Callback<Dairy.ServerDairy>() {
                            @Override
                            public void onResponse(@NonNull Call<Dairy.ServerDairy> call, @NonNull Response<Dairy.ServerDairy> response) {

                                    if(response.isSuccessful()){
                                        dbResponse.setValue(response.body());
                                    }
                                    else if (response.code()==401) getNewToken();

                            }

                            @Override
                            public void onFailure(Call<Dairy.ServerDairy> call, Throwable t) {
                                t.printStackTrace();

                            }
                        });}
            );

        }
    }

    public void getNewToken(){
        Log.i("new Token","");
        UserModel u = new UserModel();
        u.setRefreshToken(sharedPreferenceServices.GetRefreshToken(context));
        userAPI.RefreshToken(u).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                Log.i("response code",response.code()+"");
                if(response.isSuccessful()){
                    Log.i("refresh token",response.body().getRefreshToken());
                    Log.i("access token",response.body().getAuthToken());
                    sharedPreferenceServices.SetRefreshToken(context,response.body().getRefreshToken());
                    sharedPreferenceServices.SetRefreshToken(context,response.body().getAuthToken());
                }
                else if(response.code()==401){
                    //LOG OUT
                    Log.i("response code 401",response.code()+"");
                    sharedPreferenceServices.SetLogedIn(context.getApplicationContext(),false);
                    restartApp();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                // LOG OUT
                sharedPreferenceServices.SetLogedIn(context,false);
                restartApp();
            }
        });
    }
    private void restartApp() {
        Intent intent = new Intent(context.getApplicationContext(), SplashScreenActivity.class);
        context.deleteDatabase("Dairy_DB");
        int mPendingIntentId = 0;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context.getApplicationContext(), mPendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }


    void getToken(OnSuccessListener<GetTokenResult> successListener){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).
                    addOnSuccessListener(successListener)
                    .addOnFailureListener(
                            f->{
                        Toast.makeText(context,f.getMessage()+"",Toast.LENGTH_LONG).show();
                        Log.i("Token Result",f.getMessage());
                        f.printStackTrace();
                    });
        }else{
            sharedPreferenceServices.SetLogedIn(context,false);
            restartApp();
        }

    }

}
