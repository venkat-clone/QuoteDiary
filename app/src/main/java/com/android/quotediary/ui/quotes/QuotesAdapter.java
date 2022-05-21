package com.android.quotediary.ui.quotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.quotediary.R;
import com.android.quotediary.databinding.QuoteListItemBinding;
import com.android.quotediary.models.DataModelOther;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {
    Context context;
    List<DataModelOther.Quote> list;
    QuotesViewModel quotesViewModel;

    public QuotesAdapter(Context context, List<DataModelOther.Quote> list, QuotesViewModel quotesViewModel) {
        this.context = context;
        this.list = list;
        this.quotesViewModel = quotesViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(QuoteListItemBinding.inflate(
                LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.quote.setText(list.get(position).getQuote());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quotesViewModel.getSelectedQuote().setValue(list.get(holder.getAdapterPosition()).getQuote());
                quotesViewModel.selectedpos.setValue(holder.getAdapterPosition());
            }
        });
    }

    public void Update(List<DataModelOther.Quote> list){
        if(getItemCount()==0){
            this.list = list;
            notifyItemRangeInserted(0,list.size());
        }else {
            int i = getItemCount();
            this.list.addAll(list);
            notifyItemRangeInserted(i,list.size());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        QuoteListItemBinding binding;
        public ViewHolder(@NonNull QuoteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
