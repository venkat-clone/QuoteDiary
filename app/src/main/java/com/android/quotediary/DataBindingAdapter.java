package com.android.quotediary;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.android.quotediary.models.Dairy;
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
    @BindingAdapter("showerror")
    public static void showerror(TextInputLayout textInputLayout,String text){
        textInputLayout.setError(text);
        if (!text.equals("")){
            textInputLayout.getEditText().setOnTouchListener(new View.OnTouchListener() {
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
    public static void month(TextView textView, Dairy.My_Date date){
        Calendar calendar = Calendar.getInstance();
        DateFormatSymbols symbols = DateFormatSymbols.getInstance();
        Log.i("Month",""+calendar);
        calendar.set(Calendar.DAY_OF_YEAR,date.getDay_year());
        textView.setText(" "+symbols.getMonths()[calendar.get(Calendar.MONTH)].substring(0,3));
    }
    @SuppressLint("SetTextI18n")
    @BindingAdapter("day")
    public static void day(TextView textView, Dairy.My_Date date){
        Calendar calendar = Calendar.getInstance();
        Log.i("Month",""+calendar);
        calendar.set(Calendar.DAY_OF_YEAR,date.getDay_year());
        textView.setText(calendar.get(Calendar.DAY_OF_MONTH)+"");
    }




}
