package com.android.quotediary.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.quotediary.R;
import com.android.quotediary.databinding.ActivityWallPaperBinding;
import com.android.quotediary.models.DataModelOther;

import java.io.IOException;

public class WallPaperActivity extends AppCompatActivity {

    ActivityWallPaperBinding binding;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWallPaperBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ClickHandler());
        Intent intent = getIntent();
        if(intent.hasExtra("wallpaper")) binding.setModel(new DataModelOther.Wallpaper(intent.getStringExtra("wallpaper")));
        binding.image.setDrawingCacheEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }

    public class ClickHandler{
        public void ApplyWallpaper(View view){
            final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(binding.image.getDrawingCache());
                Toast.makeText(getBaseContext(),"WallPaper Applied Sucessfully ",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void onBackPressed(View view){
            finish();
        }
        public void ShowOptions(View view){
            // Show Opsctions for
        }

    }
}