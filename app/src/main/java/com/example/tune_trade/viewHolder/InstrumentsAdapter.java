package com.example.tune_trade.viewHolder;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.tune_trade.database.entities.Product;

import java.util.List;

public class InstrumentsAdapter extends ListAdapter<Product,InstrumentsViewHolder> {
    public InstrumentsAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public InstrumentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return InstrumentsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentsViewHolder holder, int position) {
        Product current = getItem(position);
        holder.bind(current.toString());
    }

    public static class instrumentDiff extends DiffUtil.ItemCallback<Product>{
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    }

}
