package com.android.quotediary.Reterofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ReterofirServices {

    public static Retrofit CreateReterofitServicers(){
        return new Retrofit.Builder()
                .baseUrl("https://my-mood-my-diary.herokuapp.com")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
    }

}
