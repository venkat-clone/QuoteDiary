package com.android.quotediary.Reterofit;

import com.android.quotediary.Helpers.BaseClass;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ReterofirServices {

    public static Retrofit CreateReterofitServicers(){
        return new Retrofit.Builder()
                .baseUrl(BaseClass.URL)
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
    }

//    public static void createClient(){
//        return new OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.MINUTES)
//                .addNetworkInterceptor(headersInterceptor)
//                .addNetworkInterceptor(tokenInterceptor)
//                .addInterceptor(httpLoggingInterceptor)
//                .build();
//    }

}
