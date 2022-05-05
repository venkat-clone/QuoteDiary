package com.android.quotediary.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.quotediary.R;
import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.DairyYear;

import java.util.List;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    List<Dairy.Year> list;
    HomeViewModel homeViewModel;
    Context context;
    MutableLiveData<YearAdapter.ViewHolder> selectedView;

    public YearAdapter(Context context, HomeViewModel homeViewModel, List<Dairy.Year> list) {
        this.context = context;
        this.homeViewModel = homeViewModel;
        this.list = list;
    }

    @NonNull
    @Override
    public YearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.year_item, parent, false));
    }

    public void Update(List<Dairy.Year> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void Merge(){
        if(list.size()>homeViewModel.oldPos){
            notifyItemChanged(homeViewModel.oldPos);
            list.get(homeViewModel.oldPos).selected = false;
        }else homeViewModel.oldPos = 0;
    }

    @Override
    public void onBindViewHolder(@NonNull YearAdapter.ViewHolder holder, int position) {
        ((TextView) holder.itemView.findViewById(R.id.year)).setText(String.valueOf(list.get(position).getYear()));
        holder.context = context;
        holder.homeViewModel = homeViewModel;
        selectedView = new MutableLiveData<>();
        holder.dairyYear = list.get(position);
        holder.setBG(homeViewModel.selectedYear.getValue()!=null && list.get(position).getYear()==homeViewModel.selectedYear.getValue().getYear() );
//        if(holder.dairyYear.selected && homeViewModel.selectedYear.getValue()==null) homeViewModel.selectedYear.setValue(holder.dairyYear);
//        if(getItemCount()-1==position) holder.show();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        HomeViewModel homeViewModel;
        Dairy.Year dairyYear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onClick(View view) {
            if(!dairyYear.selected){
                itemView.setBackground(context.getResources().getDrawable(R.drawable.rounded_background));
                dairyYear.selected = true;
                homeViewModel.oldPos = homeViewModel.newPos;
                homeViewModel.newPos = getAdapterPosition();
                homeViewModel.selectedYear.postValue(dairyYear);
            }

        }


        @SuppressLint("UseCompatLoadingForDrawables")
        public void setBG(boolean sel){
            if(sel) itemView.setBackground(context.getResources().getDrawable(R.drawable.rounded_background));
            else itemView.setBackgroundColor(Color.TRANSPARENT);
        }



    }
}
