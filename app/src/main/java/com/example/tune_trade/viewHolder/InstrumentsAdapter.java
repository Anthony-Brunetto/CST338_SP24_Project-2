package com.example.tune_trade.viewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.tune_trade.R;
import com.example.tune_trade.database.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class InstrumentsAdapter extends ListAdapter<Product,InstrumentsViewHolder> {
    public List<Product> productList = new ArrayList<>();
    public OnAddToCartClickListener addToCartClickListener;

    public interface OnAddToCartClickListener {
        void onAddToCartClick(int productId);
    }

    public void setOnAddToCartClickListener(OnAddToCartClickListener listener) {
        this.addToCartClickListener = listener;
    }
    public InstrumentsAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public InstrumentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_recycler_item, parent, false);
        return new InstrumentsViewHolder(itemView);
//        return InstrumentsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentsViewHolder holder, int position) {
        Product current = getItem(position);
        holder.bind(current.toString());
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addToCartClickListener != null){
                    addToCartClickListener.onAddToCartClick(current.getId());
                }
            }
        });
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

    @SuppressLint("NotifyDataSetChanged")
    public void setProductList(List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }
}
