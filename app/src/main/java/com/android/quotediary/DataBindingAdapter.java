package com.android.quotediary;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.android.quotediary.Helpers.BaseClass;
import com.android.quotediary.models.Dairy;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

//@Bindin
public class DataBindingAdapter {

    @BindingAdapter("setnewUsertext")
    public static void setnewUsertext(TextView textView,int s){
        Log.i("MYADAPTER","NO WORK DONE HAER");
        switch (s){
            case 1:
                textView.setText(Html.fromHtml("New User? <font color='00B7FF'>Register</font>"),TextView.BufferType.SPANNABLE);
                break;
            case 2:
                textView.setText(Html.fromHtml("AlReady Have Account? <font color='00B7FF'>LogIn</font>"),TextView.BufferType.SPANNABLE);
                break;
        }

    }

    @BindingAdapter({"message","errorCode"})
    public static void showerror(TextInputLayout textInputLayout,Integer message,String errorType){
        if(message==null) Log.i("Log_i","true");
        if(message==null || errorType == null) return;

        switch (errorType){
            case "email":
                if(message==1){
                    textInputLayout.setErrorEnabled(false);
                }
                else {
                    textInputLayout.setError("Invalid Email");
                }
                break;
            case "password":

                break;
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("showerror")
    public static void showerror(TextInputLayout textInputLayout,String text){
        textInputLayout.setError(text);
        if (!text.equals("")){
            Objects.requireNonNull(textInputLayout.getEditText()).setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    LoginActivity.viewModel.ErrorMessage.setValue("");
                    return false;
                }
            });
        }
    }
    @SuppressLint("SetTextI18n")
    @BindingAdapter("month")
    public static void month(TextView textView, Dairy date){
        Calendar calendar = Calendar.getInstance();
        DateFormatSymbols symbols = DateFormatSymbols.getInstance();
        Log.i("Month",""+calendar);
        calendar.set(Calendar.DAY_OF_YEAR,date.getDay());
        textView.setText(" "+symbols.getMonths()[calendar.get(Calendar.MONTH)].substring(0,3));
    }
    @SuppressLint("SetTextI18n")
    @BindingAdapter("day")
    public static void day(TextView textView, Dairy date){
        Calendar calendar = Calendar.getInstance();
        Log.i("Month",""+calendar);
        calendar.set(Calendar.DAY_OF_YEAR,date.getDay());
        textView.setText(calendar.get(Calendar.DAY_OF_MONTH)+"");
    }

    @BindingAdapter("SetWallpaperImage")
    public static void SetWallpaperImage(ImageView imageView,String url){
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .encodeQuality(40)
                    .into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @BindingAdapter("DeviceHeightRation")
    public static void setDeviceHeightRation(View view,double DeviceHeightRation){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (BaseClass.DeviceHeight *DeviceHeightRation);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter({"WallpaperHeight","WallpaperWidth"})
    public static void setWallpaperDimenction(View view,Integer WallpaperHeight,Integer WallpaperWidth){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//
//
        layoutParams.height = (WallpaperHeight/WallpaperWidth)*layoutParams.width;
        view.setLayoutParams(layoutParams);
    }
    @BindingAdapter("Setfont")
    public static void Setfont(TextView tv,String Setfont){
        Typeface typeface = Typeface.createFromAsset(tv.getResources().getAssets(), "fonts/"+ Setfont+".ttf");
//        Typeface typeface = tv.getResources()(R.font.font0001);
        tv.setTypeface(typeface);
    }




}
