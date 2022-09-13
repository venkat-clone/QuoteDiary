package com.android.quotediary;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.android.quotediary.Helpers.BaseClass;
import com.android.quotediary.models.Dairy;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import kotlin.random.Random;

//@Bindin
public class DataBindingAdapter {

    @BindingAdapter("setnewUsertext")
    public static void setnewUsertext(TextView textView,int s){
        Log.i("MYADAPTER","NO WORK DONE HAER");
        switch (s){
            case 1:
                textView.setText(Html.fromHtml("New User? <font color='#00B7FF'>Register</font>"),TextView.BufferType.SPANNABLE);
                break;
            case 2:
                textView.setText(Html.fromHtml("AlReady Have Account? <font color='#00B7FF'>LogIn</font>"),TextView.BufferType.SPANNABLE);
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
    public static void month(TextView textView, Dairy.ServerDairy date){
        Calendar calendar = Calendar.getInstance();
        DateFormatSymbols symbols = DateFormatSymbols.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,date.getDay());
        textView.setText(" "+symbols.getMonths()[calendar.get(Calendar.MONTH)].substring(0,3));
    }
    @SuppressLint("SetTextI18n")
    @BindingAdapter("day")
    public static void day(TextView textView, Dairy.ServerDairy date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,date.getDay());
        textView.setText(calendar.get(Calendar.DAY_OF_MONTH)+"");
    }

    @BindingAdapter("SetWallpaperImage")
    public static void SetWallpaperImage(ImageView imageView,String url){
        try {
            Shimmer.ColorHighlightBuilder shimmerBuilder = new Shimmer.ColorHighlightBuilder()
                    .setBaseColor(ContextCompat.getColor(imageView.getContext(), R.color.shimmer_gray))
                    .setHighlightColor(ContextCompat.getColor(imageView.getContext(), R.color.shimmer_gray))
                    .setDuration(1000)
                    .setIntensity(0.1f)
//                    .setDropoff(0.9f)
                    .setBaseAlpha(0.9f)
                    .setHighlightAlpha(0.7f);
            Shimmer shimmer = shimmerBuilder.build();

            ShimmerDrawable shimmerDrawable = new ShimmerDrawable();

            shimmerDrawable.setShimmer(shimmer);


            Glide.with(imageView.getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(shimmerDrawable)
                    .encodeQuality(10)
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
    @BindingAdapter("DeviceWidthRation")
    public static void setDeviceWidthRation(View view,double DeviceHeightRation){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (BaseClass.DeviceWidth *DeviceHeightRation);
        view.setLayoutParams(layoutParams);
    }


    @BindingAdapter("Setfont")
    public static void Setfont(TextView tv,String Setfont){
        Typeface typeface = Typeface.createFromAsset(tv.getResources().getAssets(), "fonts/"+ Setfont+".ttf");
//        Typeface typeface = tv.getResources()(R.font.font0001);
        tv.setTypeface(typeface);
    }


    @BindingAdapter("setRandom")
    public static void setRandom(View view,String setRandom){
        int i = Random.Default.nextInt(7);
        int color =0;

                switch (i){
//            case 0:
//                color = view.getResources().getColor(R.color.Moss);
//                break;
            case 1:
                color = view.getResources().getColor(R.color.Artichoke);
                break;
            case 2:
                color = view.getResources().getColor(R.color.Stone);
                break;
            case  3:
                color = view.getResources().getColor(R.color.Indigo);
                break;
            case 4:
                color = view.getResources().getColor(R.color.Berry);
                break;
            case  5:
                color = view.getResources().getColor(R.color.Amber);
                break;
            case 6:
                color = view.getResources().getColor(R.color.Olive);
                break;
            case 0:
            case 7:
            default:
                color = view.getResources().getColor(R.color.Moss);
                break;
        }
        view.setBackgroundColor(color);
    }

    @BindingAdapter("isShimmer")
    public static void setShimmer(ShimmerFrameLayout shimmer,boolean isShimmer){
        if(isShimmer){
            shimmer.startShimmer();
        }
        else shimmer.stopShimmer();
    }
    @BindingAdapter("setDay")
    public static void setDat(ImageView imageView,Dairy.ServerDairy date){
//        String colors[] = imageView.getContext().getResources().getStringArray(R.array.week);
        Calendar calendar = Calendar.getInstance();
        DateFormatSymbols symbols = DateFormatSymbols.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,date.getDay());
        calendar.set(Calendar.YEAR,date.getYear());
        int[] colors = imageView.getContext().getResources().getIntArray(R.array.weekInt);
        imageView.setColorFilter(colors[calendar.get(Calendar.DAY_OF_WEEK)-1], android.graphics.PorterDuff.Mode.MULTIPLY);

    }



}
