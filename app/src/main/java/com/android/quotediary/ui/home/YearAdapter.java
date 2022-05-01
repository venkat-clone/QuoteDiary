package com.android.quotediary.ui.home;

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
import com.android.quotediary.models.DairyYear;

import java.util.List;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    List<DairyYear> list;
    HomeViewModel homeViewModel;
    Context context;
    MutableLiveData<YearAdapter.ViewHolder> selectedView;

    public YearAdapter(Context context, HomeViewModel homeViewModel, List<DairyYear> list) {
        this.context = context;
        this.homeViewModel = homeViewModel;
        this.list = list;
    }

    @NonNull
    @Override
    public YearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.year_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull YearAdapter.ViewHolder holder, int position) {
        ((TextView) holder.itemView.findViewById(R.id.year)).setText(list.get(position).getYear());
        holder.context = context;
        holder.homeViewModel = homeViewModel;
        selectedView = new MutableLiveData<>();
        holder.dairyYear = list.get(position);
        if(getItemCount()-1==position) holder.show();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        HomeViewModel homeViewModel;
        DairyYear dairyYear;
        boolean isSelected = false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(!isSelected) show();


        }

        public void show(){
            isSelected= true;
            if(homeViewModel.selectedHolder.getValue()!=null)
                homeViewModel.selectedHolder.getValue().hide();
            itemView.setBackground(context.getResources().getDrawable(R.drawable.rounded_background));
//            itemView.setEnabled(true);
//            itemView.invalidate();
            homeViewModel.selectedHolder.postValue(this);
            homeViewModel.selectedYear.setValue(dairyYear);
        }

        public void hide(){
            isSelected = false;
//            itemView.setEnabled(false);
            itemView.setBackgroundColor(Color.TRANSPARENT);
        }



    }
}
