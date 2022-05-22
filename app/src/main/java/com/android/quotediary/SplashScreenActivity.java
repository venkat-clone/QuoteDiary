package com.android.quotediary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.quotediary.Helpers.BaseClass;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        BaseClass.DeviceHeight = displayMetrics.heightPixels;
        BaseClass.DeviceWidth = displayMetrics.widthPixels;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferenceServices.IsFirstRun(getBaseContext())){
                    Log.i("Splash Screen","First Run");
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                else if(sharedPreferenceServices.IsLogedIn(getBaseContext())){
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    Log.i("Splash Screen","LogedIn");

                }
                else{
                    Log.i("Splash Screen","Else ");
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(i);
                    finish();
                }
            }
        }, 300);
    }



}