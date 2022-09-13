package com.android.quotediary.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.app.usage.ExternalStorageStats;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.quotediary.MainActivity;
import com.android.quotediary.R;
import com.android.quotediary.databinding.ActivityWallPaperBinding;
import com.android.quotediary.models.DataModelOther;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class WallPaperActivity extends AppCompatActivity {

    ActivityWallPaperBinding binding;
    Bitmap bitmap;
    String url;
    boolean applied = false;
    boolean downloaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWallPaperBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        super.onCreate(savedInstanceState);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ClickHandler());
        binding.image.setDrawingCacheEnabled(true);
        setContentView(binding.getRoot());
        url= getIntent().getStringExtra("wallpaper");
        Glide.with(this).load(getIntent().getStringExtra("wallpaper")).into(binding.image);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public class ClickHandler{

        public void shareWallpaper(View view){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,url);
            intent.setType("text/plain");
            startActivity(intent);
        }

        public void DownloadWallPaper(View view){

            DownloadImage(getBaseContext(),url);
        }


        public void ApplyWallpaper(View view){
            if(applied) {
                Toast.makeText(getBaseContext(),"WallPaper Already Applied ",Toast.LENGTH_LONG).show();
                return;
            }
            final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(binding.image.getDrawingCache());
                applied = true;
                Toast.makeText(getBaseContext(),"WallPaper Applied Successfully ",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void onBackPressed(View view){
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        public void ShowOptions(View view){
            // Show Opsctions for
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==120 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            DownloadImage(getBaseContext(),url);
        }
        else if(requestCode==120) {
            Dialog dailg = new Dialog(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200,200);
            TextView textView = new TextView(this);
            dailg.addContentView(textView,layoutParams);
            dailg.show();
        }
    }

    public void DownloadImage(Context context, String url){
        if(downloaded){
            Snackbar.make(binding.getRoot(),"Wallpaper Already Downloaded",Snackbar.LENGTH_LONG).show();
            return ;
        }
        if (ActivityCompat.checkSelfPermission(WallPaperActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WallPaperActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},120);
            return;
        }
        ProgressDialog d = new ProgressDialog(this);
        d.setMessage("Downloading...");
        d.show();
        Glide.with(context)
                .as(Bitmap.class)
                .load(url)
                .encodeQuality(100)
                .into(new SimpleTarget<Bitmap>() {
                    @SuppressLint({"SetWorldWritable", "SetWorldReadable"})
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                        File sdCard = Environment.getExternalStorageDirectory();
                        @SuppressLint("DefaultLocale") String fileName = String.format("%d.jpg", System.currentTimeMillis());
                        File dir = new File(sdCard.getAbsolutePath() + "/DCIM/QuoteDairy");
                        //noinspection ResultOfMethodCallIgnored
                        dir.mkdirs();
                        final File myImageFile = new File(dir, fileName);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +dir.getPath())));
                            d.cancel();
                            Toast.makeText(context,"Successfully Downloaded Wallpaper",Toast.LENGTH_SHORT).show();
                        }catch (IOException e) {
                            e.printStackTrace();
                            d.cancel();
                        } finally {
                            try {
                                if(fos!=null) fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
    }

}