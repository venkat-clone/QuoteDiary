package com.android.quotediary.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.app.usage.ExternalStorageStats;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.quotediary.R;
import com.android.quotediary.databinding.ActivityWallPaperBinding;
import com.android.quotediary.models.DataModelOther;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class WallPaperActivity extends AppCompatActivity {

    ActivityWallPaperBinding binding;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWallPaperBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        getWindow().addFlags(~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ClickHandler());
        Intent intent = getIntent();
        if(intent.hasExtra("wallpaper")) binding.setModel(new DataModelOther.Wallpaper(intent.getStringExtra("wallpaper")));
        else binding.setModel(new DataModelOther.Wallpaper("https://images.unsplash.com/photo-1568149788227-36c45e231c3c"));
        binding.image.setDrawingCacheEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }

    public class ClickHandler{

        public void shareWallpaper(View view){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,binding.getModel().getUrl());
            intent.setType("text/plain");
            startActivity(intent);
        }

        public void DownloadWallPaper(View view){

            DownloadImage(getBaseContext(),binding.getModel().getUrl());
        }


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
    public void DownloadImage(Context context, String url){
        Glide.with(context)
                .as(Bitmap.class)
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @SuppressLint({"SetWorldWritable", "SetWorldReadable"})
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                        File sdCard = Environment.getExternalStorageDirectory();
                        @SuppressLint("DefaultLocale") String fileName = String.format("%d.jpg", System.currentTimeMillis());
                        File dir = new File(sdCard.getAbsolutePath() + "/DCIM/QuoteDairy");
                        dir.setReadable(true,false);
                        dir.setWritable(true,false);
                        dir.mkdirs();
                        final File myImageFile = new File(dir, fileName);
                        myImageFile.setWritable(true,false);
                        myImageFile.setReadable(true,false);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            Bitmap bitmap = resource;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +dir.getPath())));
                            Toast.makeText(context,"Successfully Downloaded Wallpaper",Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
    }

}