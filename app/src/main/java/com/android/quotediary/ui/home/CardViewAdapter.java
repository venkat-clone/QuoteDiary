package com.android.quotediary.ui.home;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.android.quotediary.R;
import com.android.quotediary.databinding.DairyItemBinding;
import com.android.quotediary.models.Dairy;

import java.util.ArrayList;
import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    List<Dairy.ServerDairy> dairies;
    Context context;
    HomeViewModel homeViewModel;
    int clicked =-1;
    int selectedPosition = -1;
    private int lastPosition =-1;
    float InitalDate = 25,FinalDate = 52;
    float InitalText = 0,FinalText = 20;
    public CardViewAdapter(List<Dairy.ServerDairy> dairies, Context context,HomeViewModel homeViewModel) {
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
//        if(null!=homeViewModel.DbResponse.getValue() &&
//        !homeViewModel.DbResponse.getValue() && dairies.get(position).isToday && selectedPosition>-1) holder.onTextChanged("",1,1,1);
//        if(dairies.get(position).isToday) holder.dairyItemBinding.content.
//        if(!dairies.get(position).getContent().isEmpty()) holder.dairyItemBinding.content.setText(dairies.get(position).getContent());
        setAnimation(holder.dairyItemBinding.cardView,position);
        holder.dairyItemBinding.content.setEnabled(false);
        holder.setSelected(dairies.get(position));
        holder.dairyItemBinding.setHolder(holder);
//        if(dairies.get(position).isToday && selectedPosition==-1) holder.ExpandAnimation();
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

    public void Merge(){
//        if(ToClose!=-1)notifyItemChanged(ToClose);
    }

    @Override
    public int getItemCount() {
        return dairies.size();
    }

    public void Update(List<Dairy.ServerDairy> dairies) {
        selectedPosition = -1;
        clicked =-2;
        int i = this.dairies.size();
        this.dairies = new ArrayList<>();
        this.notifyItemRangeRemoved(0,i);
        this.dairies = dairies;
        this.notifyItemRangeInserted(0,dairies.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        DairyItemBinding dairyItemBinding;

        int InitalCard ,FinalCard;
        int InitalTextColor ,FinalTextColor;
        final int DURATION=1000;
        public ViewHolder(@NonNull DairyItemBinding itemView) {
            super(itemView.getRoot());
            dairyItemBinding = itemView;
//            dairyItemBinding.setClickhandler(this);
            this.InitalCard = dairyItemBinding.cardView.getCardBackgroundColor().getDefaultColor();
            TypedValue value = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.Dairy_cardBackground, value, true);
            this.FinalCard = value.data;
            this.InitalTextColor = itemView.dateText.getCurrentTextColor();
            context.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, value, true);
            this.FinalTextColor = value.data;
        }

        void setSelected(final Dairy.ServerDairy dairy){
            if(selectedPosition==-1){
                // Hide Hear
                if(clicked>0){
                    CompressAnimation();
                    clicked =-1;
                }else Init();
            }
            else {
                if(getAdapterPosition()==selectedPosition){
                    // show Hear
                    if(clicked>0){
                        ExpandAnimation();
                        clicked =-1;
                    }else Final();
                }
                else {
                    // HIde Hear
                    if(clicked>0){
                        CompressAnimation();
                        clicked--;
                    }else Init();

                }
            }
        }

        public void onClicked(View view){
            // visable
            clicked =2;
            if(selectedPosition != getAdapterPosition()){
                notifyItemChanged(selectedPosition);
                selectedPosition = getAdapterPosition();
                ExpandAnimation();
            }
            else {
                selectedPosition = -1;
                CompressAnimation();
                    clicked =-1;
            }


        }
        public void save(View view,Dairy.ServerDairy dairy){
            view.setVisibility(View.GONE);
            homeViewModel.uploadToday.postValue(dairy);

        }
        // i length , i1 number of chenges i1 = initial len i2= final
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if( dairyItemBinding.getDairy().isToday && selectedPosition!=-1 && dairyItemBinding.content.isEnabled())
                dairyItemBinding.save.setVisibility(View.VISIBLE);
            else dairyItemBinding.save.setVisibility(View.GONE);
        }

        public void ExpandAnimation(){
            AnimatorSet animationSet;
            ValueAnimator dateAnimator,textInputAnimator,cardBGAnimation,TextColorAnimation;
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
                    float PDate = (float) valueAnimator.getAnimatedValue();
                    dairyItemBinding.dateText.setTextSize(PDate);
                }
            });
            textInputAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float PText = (float) valueAnimator.getAnimatedValue();
                    dairyItemBinding.content.setTextSize(PText);
                    if(Dairy.ServerDairy.isToday(dairyItemBinding.getDairy())){
                        if(PText==FinalText && dairyItemBinding.getDairy().isToday){
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
                    int CardColor = (int) valueAnimator.getAnimatedValue();
                    dairyItemBinding.cardView.setCardBackgroundColor(CardColor);
                }
            });
            TextColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int TextColor = (int) valueAnimator.getAnimatedValue();
                    dairyItemBinding.month.setTextColor(TextColor);
                    dairyItemBinding.dateText.setTextColor(TextColor);
                }
            });
            animationSet = new AnimatorSet();
            animationSet.play(dateAnimator);
            animationSet.play(textInputAnimator);
            animationSet.play(cardBGAnimation);
            animationSet.play(TextColorAnimation);
            animationSet.start();
        }
        public void CompressAnimation(){
            AnimatorSet animationSet;
            ValueAnimator dateAnimator,textInputAnimator,cardBGAnimation,TextColorAnimation;

            dateAnimator = ValueAnimator.ofFloat(FinalDate,InitalDate);
            textInputAnimator = ValueAnimator.ofFloat(FinalText,InitalText);
            cardBGAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),FinalCard,InitalCard);
            TextColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),FinalTextColor,InitalTextColor);
            dateAnimator.setDuration(DURATION);
            textInputAnimator.setDuration(DURATION);
            cardBGAnimation.setDuration(DURATION);
            TextColorAnimation.setDuration(DURATION);
            dateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float PDate = (float) valueAnimator.getAnimatedValue();
                    dairyItemBinding.dateText.setTextSize(PDate);
                }
            });
            textInputAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float PText = (float) valueAnimator.getAnimatedValue();
                    dairyItemBinding.content.setTextSize(PText);
                    dairyItemBinding.content.setEnabled(false);
                }
            });
            cardBGAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int CardColor = (int) valueAnimator.getAnimatedValue();
                    dairyItemBinding.cardView.setCardBackgroundColor(CardColor);
                }
            });
            TextColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int TextColor = (int) valueAnimator.getAnimatedValue();
                    dairyItemBinding.month.setTextColor(TextColor);
                    dairyItemBinding.dateText.setTextColor(TextColor);
                }
            });
            animationSet = new AnimatorSet();
            animationSet.play(dateAnimator);
            animationSet.play(textInputAnimator);
            animationSet.play(cardBGAnimation);
            animationSet.play(TextColorAnimation);
            animationSet.start();
        }
        public void Init() {
            dairyItemBinding.dateText.setTextSize(InitalDate);
            dairyItemBinding.content.setTextSize(InitalText);
            dairyItemBinding.cardView.setCardBackgroundColor(InitalCard);
            dairyItemBinding.month.setTextColor(InitalTextColor);
            dairyItemBinding.dateText.setTextColor(InitalTextColor);
        }
        public void Final() {
            dairyItemBinding.dateText.setTextSize(FinalDate);
            dairyItemBinding.content.setTextSize(FinalText);
            dairyItemBinding.cardView.setCardBackgroundColor(FinalCard);
            dairyItemBinding.month.setTextColor(FinalTextColor);
            dairyItemBinding.dateText.setTextColor(FinalTextColor);
        }
    }
}




















