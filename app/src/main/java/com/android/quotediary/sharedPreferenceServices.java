package com.android.quotediary;

import android.content.Context;

public class sharedPreferenceServices {
    public static final String SharedKey="SharedPrifs";

    public static boolean IsFirstRun( Context context){
        return context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).getBoolean("isFirstRun",true);
    }
    public static void SetFirstRun( Context context){
         context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).edit().putBoolean("isFirstRun",false).apply();
    }
    public static boolean IsLogedIn(Context context){
        return context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).getBoolean("isLogedIn",false);
    }
    public static void SetLogedIn(Context context,boolean islogin){
        context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).edit().putBoolean("isLogedIn",islogin).apply();
    }

    public static String GetAuthToken(Context context){
        return context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).getString("AuthToken","null");
    }
    public static void SetAuthToken(Context context,String AuthToken){
        context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).edit().putString("AuthToken",AuthToken).apply();
    }
    public static String GetRefreshToken(Context context){
        return context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).getString("RefreshToken","null");
    }
    public static void SetRefreshToken(Context context,String AuthToken){
        context.getSharedPreferences(SharedKey,Context.MODE_PRIVATE).edit().putString("RefreshToken",AuthToken).apply();
    }

}
