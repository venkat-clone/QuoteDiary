package com.android.quotediary.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;

public class BaseClass {
    public static int DeviceWidth;
    public static int DeviceHeight;
    public static String URL = "<API URL>";
//    public static final String URL = "https://198.168.1.14:4000";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public static void setColor(){

    }
}
