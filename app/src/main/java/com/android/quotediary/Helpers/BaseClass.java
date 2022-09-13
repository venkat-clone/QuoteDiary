package com.android.quotediary.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import com.android.quotediary.BuildConfig;

public class BaseClass {
    public static int DeviceWidth;
    public static int DeviceHeight;
    public static String URL = "https://my-mood-my-diary.herokuapp.com";
//    public static String URL = BuildConfig.API;
//    public static final String URL = "http://192.168.42.43:4000";
//    public static final String URL = "https://127.0.0.1:4000";
//    public static final String URL = "https://localhost:4000";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
