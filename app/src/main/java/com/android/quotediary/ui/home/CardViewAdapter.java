package com.android.quotediary.ui.home;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.quotediary.R;
import com.android.quotediary.databinding.DairyItemBinding;
import com.android.quotediary.models.Dairy;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    List<Dairy> dairies;
    Context context;
    private int lastPosition =-1;
    public CardViewAdapter(List<Dairy> dairies, Context context) {
        this.dairies = dairies;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DairyItemBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.ViewHolder holder, int position) {
        holder.dairyItemBinding.setDairy(dairies.get(position));
        setAnimation(holder.dairyItemBinding.cardView,position);

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            animation.setDuration(800);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
        else if (position < lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            animation.setDuration(800);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return dairies.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        DairyItemBinding dairyItemBinding;
        float PText=0;
        float PDate=25;
        DairyItemBinding Expanded ;
        CountDownTimer countDownTimer ;
        public ViewHolder(@NonNull DairyItemBinding itemView) {
            super(itemView.getRoot());
            dairyItemBinding = itemView;
            dairyItemBinding.setExpand(false);
            dairyItemBinding.setClickhandler(this);
        }
        public void show(View view,boolean exand){


            if(!exand){
                // for text
                ValueAnimator dateAnimator = ValueAnimator.ofFloat(PDate, 52);
                dateAnimator.setDuration(1000);
                dateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        PDate = (float) valueAnimator.getAnimatedValue();
                        dairyItemBinding.dateText.setTextSize(PDate);
                        Log.i("Value-DI",""+PDate);
                    }
                });
                // for textInput
                ValueAnimator textInputAnimator = ValueAnimator.ofFloat(PText,20);
                textInputAnimator.setDuration(1000);
                textInputAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        PText = (float) valueAnimator.getAnimatedValue();
                        dairyItemBinding.content.setTextSize(PText);
                        Log.i("Value-TI",""+PText);
                    }
                });
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = context.getTheme();

                // trigger all at same time
                textInputAnimator.start();
                dateAnimator.start();
                dairyItemBinding.month.getLayout().getOffsetForHorizontal(10,100);
                dairyItemBinding.setExpand(true);

            }
            else {
                // compress hear
// for text
                ValueAnimator dateAnimator = ValueAnimator.ofFloat(PDate, 24);
                dateAnimator.setDuration(1000);
                dateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        PDate = (float) valueAnimator.getAnimatedValue();
                        dairyItemBinding.dateText.setTextSize(PDate);
                        Log.i("Value-DD",""+PDate);
                    }
                });
                // for textInput
                ValueAnimator textInputAnimator = ValueAnimator.ofFloat(PText,0);
                textInputAnimator.setDuration(1000);
                textInputAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        PText = (float) valueAnimator.getAnimatedValue();
                        Log.i("Value-TD",""+valueAnimator.getAnimatedValue());
                        dairyItemBinding.content.setTextSize(PText);
                        if(PText==0.0f)
                            dairyItemBinding.setExpand(false);

                    }

                });



                // trigger all at same time
                textInputAnimator.start();
                dateAnimator.start();
                dairyItemBinding.month.getLayout().getOffsetForHorizontal(10,100);

            }


//            Log.i("dairy","happening");

//            int m = !exand?android.R.anim.fade_in:android.R.anim.fade_out;
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
//
//            animation.setDuration(800);
//            dairyItemBinding.textInputLayout.setAnimation(animation);

//            dairyItemBinding.invalidateAll();
        }
    }
}
