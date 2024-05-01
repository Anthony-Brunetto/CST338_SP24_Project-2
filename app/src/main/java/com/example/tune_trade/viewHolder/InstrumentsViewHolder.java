package com.example.tune_trade.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tune_trade.LandingPage;
import com.example.tune_trade.R;

public class InstrumentsViewHolder extends RecyclerView.ViewHolder {
    private final TextView instrumentViewItem;

    private InstrumentsViewHolder(View instrumentView){
        super(instrumentView);
        instrumentViewItem = instrumentView.findViewById(R.id.recyclerItemTextview);

    }

    public void bind(String text){
        instrumentViewItem.setText(text);
    }

    static InstrumentsViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_recycler_item,parent,false);
        return new InstrumentsViewHolder(view);
    }
}
