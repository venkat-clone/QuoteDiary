package com.android.quotediary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.quotediary.Helpers.BaseClass;
import com.android.quotediary.ui.VerifyAccountActivity;
import com.google.firebase.auth.FirebaseAuth;

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

        new Handler().postDelayed(() -> {

            if(FirebaseAuth.getInstance().getCurrentUser()==null){
                // Login Activity
                Log.i("Splash Screen","First Run");
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
            else if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                // Main Activity
                Log.i("Splash Screen","First Run");
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
            else if(!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                // VerifyActivity
                Log.i("Splash Screen","First Run");
                Intent i = new Intent(SplashScreenActivity.this, VerifyAccountActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
            else {
                // Login Activity
                Log.i("Splash Screen","First Run");
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 300);
    }



}