package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tune_trade.database.ProductDAO;
import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.databinding.ActivityInstrumentsPageBinding;
import com.example.tune_trade.viewHolder.InstrumentsAdapter;
import com.example.tune_trade.viewHolder.InstrumentsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InstrumentsPageActivity extends AppCompatActivity {
    private TuneTradeRepository repository;
    ActivityInstrumentsPageBinding binding;
    private InstrumentsViewModel instrumentsViewModel;

    List<String> instrumentlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruments_page);
        binding = ActivityInstrumentsPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        instrumentsViewModel = new ViewModelProvider(this).get(InstrumentsViewModel.class);

        RecyclerView recyclerView = binding.InstrumentDisplayReccyclerView;
        repository = TuneTradeRepository.getRepository(getApplication());
//        setupInstrumentList();
        final InstrumentsAdapter adapter = new InstrumentsAdapter(new InstrumentsAdapter.instrumentDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        instrumentsViewModel.getAllLogsByCategory("Instruments").observe(this, products -> {
            adapter.submitList(products);
        });
    }

    private void setupInstrumentList() {
        instrumentlist = (List<String>) repository.getProductsByCategory("Instruments");
    }


    public static Intent InstrumentsPageIntentFactory(Context context){
        Intent intent = new Intent(context, InstrumentsPageActivity.class);
        return intent;
    }
}