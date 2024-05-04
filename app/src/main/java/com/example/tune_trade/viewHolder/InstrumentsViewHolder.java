package com.example.tune_trade.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tune_trade.InstrumentsPageActivity;
import com.example.tune_trade.LandingPage;
import com.example.tune_trade.R;
import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.database.entities.Product;

import java.util.List;

public class InstrumentsViewHolder extends RecyclerView.ViewHolder {
    public Button addToCart;
    Product product;
    private List<Product> productList;

    private final TextView instrumentViewItem;

    public InstrumentsAdapter.OnAddToCartClickListener addToCartClickListener;

    InstrumentsViewHolder(View instrumentView){
        super(instrumentView);
        instrumentViewItem = instrumentView.findViewById(R.id.recyclerItemTextview);
        addToCart = instrumentView.findViewById(R.id.recyclerItemAddCartButton);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION){
                    int productId = (InstrumentsPageActivity.productList.get(position).getId());
                    if(addToCartClickListener != null){
                        addToCartClickListener.onAddToCartClick(productId);

                    }
                }
            }
        });

    }

    public void bind(String text){
        instrumentViewItem.setText(text);
    }

    static InstrumentsViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_recycler_item,parent,false);
        return new InstrumentsViewHolder(view);
    }
}
