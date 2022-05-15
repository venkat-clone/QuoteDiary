package com.android.quotediary.ui.quotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.quotediary.R;
import com.android.quotediary.databinding.FontItemBinding;
import com.android.quotediary.models.DataModelOther;

import java.util.List;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {

    List<DataModelOther.FontStyle> list;
    Context context;
    QuotesViewModel quotesViewModel;
    int selectedPosition = -1;

    public FontAdapter(List<DataModelOther.FontStyle> list, Context context, QuotesViewModel quotesViewModel) {
        this.list = list;
        this.context = context;
        this.quotesViewModel = quotesViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FontItemBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setSelected();
        holder.binding.setModel(list.get(position));
        holder.fontStyle = list.get(position);

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FontItemBinding binding;
        DataModelOther.FontStyle fontStyle;
        public ViewHolder(@NonNull FontItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }


        public void setSelected(){
            if(selectedPosition ==-1){
                binding.Aa.setTextColor(context.getResources().getColor(R.color.black));
                binding.name.setTextColor(context.getResources().getColor(R.color.black));

            }else {
                if(selectedPosition==getAdapterPosition()){
                    binding.Aa.setTextColor(context.getResources().getColor(R.color.purple_200));
                    binding.name.setTextColor(context.getResources().getColor(R.color.purple_200));
                }
                else {
                    binding.Aa.setTextColor(context.getResources().getColor(R.color.black));
                    binding.name.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        }



        @Override
        public void onClick(View view) {

            binding.Aa.setTextColor(context.getResources().getColor(R.color.purple_200));
            binding.name.setTextColor(context.getResources().getColor(R.color.purple_200));
            if(selectedPosition!=getAdapterPosition()){
                notifyItemChanged(selectedPosition);
                selectedPosition = getAdapterPosition();
                quotesViewModel.selected.setValue(fontStyle);
            }
        }
    }
}
