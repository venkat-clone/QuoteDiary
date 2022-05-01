package com.android.quotediary.ui.home;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.AnimatorRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import android.os.Bundle;
import android.widget.TextView;

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
    HomeViewModel homeViewModel;
    private int lastPosition =-1;
    public CardViewAdapter(List<Dairy> dairies, Context context,HomeViewModel homeViewModel) {
        this.dairies = dairies;
        this.context = context;
        this.homeViewModel = homeViewModel;
    }

    @NonNull
    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DairyItemBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.ViewHolder holder, int position) {
        holder.dairyItemBinding.setDairy(dairies.get(position));
        holder.homeViewModel = homeViewModel;
        holder.index = position-1;
//        setAnimation(holder.dairyItemBinding.cardView,position);

    }

    /**
     *  For Fade In and Fade Out Animations
     *  Scrolling Animation
     * @param viewToAnimate
     * @param position
     */
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

    public void Update(List<Dairy> dairies) {
        this.dairies = dairies;
        this.notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        DairyItemBinding dairyItemBinding;
        HomeViewModel homeViewModel;
        int index;
        float PText=0;
        float PDate=25;
        float InitalDate = 25,FinalDate = 52;
        float InitalText = 0,FinalText = 20;

        int CardColor,TextColor;
        int InitalCard ,FinalCard;
        int InitalTextColor ,FinalTextColor;
        AnimatorSet animationSet;

        ValueAnimator dateAnimator,textInputAnimator,cardBGAnimation,TextColorAnimation;
        final int DURATION=1000;
        public ViewHolder(@NonNull DairyItemBinding itemView) {
            super(itemView.getRoot());
            dairyItemBinding = itemView;
            dairyItemBinding.setExpand(false);
            dairyItemBinding.setClickhandler(this);
            this.InitalCard = dairyItemBinding.cardView.getCardBackgroundColor().getDefaultColor();
            TypedValue value = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.Dairy_cardBackground, value, true);
            this.FinalCard = value.data;
//            context.getTheme().resolveAttribute(android.R.attr.colorPrimary, value, true);
            this.InitalTextColor = itemView.dateText.getCurrentTextColor();
            context.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, value, true);
            this.FinalTextColor = value.data;
            CardColor = InitalCard;
            TextColor = InitalTextColor;
            dairyItemBinding.content.setEnabled(false);
            setAnimation();
        }

        public void setAnimation(){
            dateAnimator = ValueAnimator.ofFloat(InitalDate,FinalDate);
            textInputAnimator = ValueAnimator.ofFloat(InitalText,FinalText);
            cardBGAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),InitalCard,FinalCard);
            TextColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),InitalTextColor,FinalTextColor);

            dateAnimator.setDuration(DURATION);
            textInputAnimator.setDuration(DURATION);
            cardBGAnimation.setDuration(DURATION);
            TextColorAnimation.setDuration(DURATION);

            dateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PDate = (float) valueAnimator.getAnimatedValue();
                    dairyItemBinding.dateText.setTextSize(PDate);
                }
            });
            textInputAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PText = (float) valueAnimator.getAnimatedValue();
                    dairyItemBinding.content.setTextSize(PText);
                    if(homeViewModel.getToday().getValue().equals(dairyItemBinding.getDairy().getMy_date())){
                    if((PText==FinalText) & dairyItemBinding.getExpand() ){
                        dairyItemBinding.content.setFocusable(true);
                        dairyItemBinding.content.setEnabled(true);
                        dairyItemBinding.content.setFocusableInTouchMode(true);
                    }
                    }
//                    else {
//                        dairyItemBinding.content.setEnabled(false);
//                    }
                }
            });
            cardBGAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CardColor = (int) valueAnimator.getAnimatedValue();
                    dairyItemBinding.cardView.setCardBackgroundColor(CardColor);
                }
            });
            TextColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TextColor = (int) valueAnimator.getAnimatedValue();
                    dairyItemBinding.month.setTextColor(TextColor);
                    dairyItemBinding.dateText.setTextColor(TextColor);
                }
            });

            animationSet = new AnimatorSet();
            animationSet.play(dateAnimator);
            animationSet.play(textInputAnimator);
            animationSet.play(cardBGAnimation);
            animationSet.play(TextColorAnimation);


        }




        public void Show(View view ,boolean exand){


            if ( animationSet!=null && animationSet.isStarted()) {
                animationSet.cancel();
            }
            if(!exand){
                if(homeViewModel.getExpanded().getValue()!=null)
                    homeViewModel.getExpanded().getValue().Show(view, true);
                dateAnimator.setFloatValues(PDate,FinalDate);
                textInputAnimator.setFloatValues(PText,FinalText);
                cardBGAnimation.setIntValues(CardColor,FinalCard);
                TextColorAnimation.setIntValues(TextColor,FinalTextColor);
            }
            else {
                dateAnimator.setFloatValues(PDate,InitalDate);
                textInputAnimator.setFloatValues(PText,InitalText);
                cardBGAnimation.setIntValues(CardColor,InitalCard);
                TextColorAnimation.setIntValues(TextColor,InitalTextColor);
            }
            dairyItemBinding.setExpand(!exand);
            homeViewModel.getExpanded().postValue(this);
            animationSet.start();


        }
    }
}




















