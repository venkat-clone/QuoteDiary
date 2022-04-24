package com.android.quotediary.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.quotediary.R;
import com.android.quotediary.databinding.DairyItemBinding;
import com.android.quotediary.models.Dairy;

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
        public ViewHolder(@NonNull DairyItemBinding itemView) {
            super(itemView.getRoot());
            dairyItemBinding = itemView;
            dairyItemBinding.setExpand(false);
            dairyItemBinding.setClickhandler(this);
        }
        public void show(View view,boolean exand){
            Log.i("dairy","happening");
            int m = !exand?android.R.anim.fade_in:android.R.anim.fade_out;
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);

            animation.setDuration(800);
//            dairyItemBinding.full.setAnimation(animation);
            dairyItemBinding.setExpand(!exand);

//            dairyItemBinding.invalidateAll();
        }
    }
}
