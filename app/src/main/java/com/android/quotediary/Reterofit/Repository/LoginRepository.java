package com.android.quotediary.Reterofit.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.quotediary.LoginActivityViewModel;
import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.UserModel;
import com.android.quotediary.Reterofit.API.LoginAPI;
import com.android.quotediary.Reterofit.ReterofirServices;
import com.android.quotediary.sharedPreferenceServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository {
    LoginAPI loginAPI;
    Context context;
    public LoginRepository(Context context) {
        Retrofit reterofit = ReterofirServices.CreateReterofitServicers();
        loginAPI = reterofit.create(LoginAPI.class);
        this.context = context;
    }

    public void Login(UserModel.Login userModel, MutableLiveData<Integer> loginresponce){
        loginAPI.Login(userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"LogIn Successful",Toast.LENGTH_LONG).show();
                    if(response.body()!=null & response.body().getAuthToken()!=null & response.body().getRefreshToken()!=null){
                        sharedPreferenceServices.SetAuthToken(context,response.body().getAuthToken());
                        sharedPreferenceServices.SetRefreshToken(context,response.body().getRefreshToken());
                        sharedPreferenceServices.SetLogedIn(context,true);
                        sharedPreferenceServices.SetFirstRun(context);
                        loginresponce.setValue(200);
                    }
                    Log.i("Log_i","Successful");
                }
                else {
                    if(response.code()==404){
                        loginresponce.setValue(404);
                    }
                    else {
                        loginresponce.setValue(400);
                    }

                    Log.i("Log_i",response.message());
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.i("Log_i",t.getLocalizedMessage());
                loginresponce.setValue(400);

            }
        });

    }
    public void SignUp(UserModel.Register userModel,MutableLiveData<Integer> SignUpResponce){
        loginAPI.SignUp(userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"LogIn Successful",Toast.LENGTH_LONG).show();
                    if(response.body()!=null & response.body().getAuthToken()!=null & response.body().getRefreshToken()!=null){
                        sharedPreferenceServices.SetAuthToken(context,response.body().getAuthToken());
                        sharedPreferenceServices.SetRefreshToken(context,response.body().getRefreshToken());
                        sharedPreferenceServices.SetLogedIn(context,true);
                        sharedPreferenceServices.SetFirstRun(context);
                        Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show();
                    }
                    SignUpResponce.setValue(200);
                    Log.i("Log_i","Successful");
                }
                else {
                    if (response.code()==404) SignUpResponce.setValue(404);
                    Toast.makeText(context,"Un Successful",Toast.LENGTH_LONG).show();
                    Log.i("Log_i",response.message());
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.i("Log_i",t.getLocalizedMessage());
                Toast.makeText(context,"LogIn Failed",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void Download(MutableLiveData<Integer> responcecode,MutableLiveData<List<Dairy.ServerDairy>> list){
        loginAPI.getDairyCount(sharedPreferenceServices.GetAuthToken(context)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        int i = json.getInt("count");
                        if(i>0){
                            loginAPI.getDairys(sharedPreferenceServices.GetAuthToken(context)).enqueue(new Callback<List<Dairy.ServerDairy>>() {
                                @Override
                                public void onResponse(Call<List<Dairy.ServerDairy>> call, Response<List<Dairy.ServerDairy>> response) {
                                    if(response.isSuccessful()){
                                        list.setValue(response.body());
                                    }

                                }

                                @Override
                                public void onFailure(Call<List<Dairy.ServerDairy>> call, Throwable t) {
                                    Toast.makeText(context,"PLease Try Again",Toast.LENGTH_SHORT).show();

                                }
                            });
                        }else {
                            responcecode.setValue(200);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"PLease Try Again",Toast.LENGTH_SHORT).show();
            }
        });


    }



}
