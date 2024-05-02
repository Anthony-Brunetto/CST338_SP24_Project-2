package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityInstrumentsPageBinding;
import com.example.tune_trade.databinding.ActivityVinylsBinding;
import com.example.tune_trade.viewHolder.InstrumentsAdapter;
import com.example.tune_trade.viewHolder.InstrumentsViewModel;

public class VinylsActivity extends AppCompatActivity { // TODO: ADD BACK BUTTON OR GO BACK TO MAIN ACTIVITY
    private static final String LANDING_PAGE_USER_ID = "com.example.tune_trade.LANDING_PAGE_USER_ID";
    private TuneTradeRepository repository;
    ActivityVinylsBinding binding;
    private InstrumentsViewModel instrumentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinyls);
        binding = ActivityVinylsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        instrumentsViewModel = new ViewModelProvider(this).get(InstrumentsViewModel.class);

        RecyclerView recyclerView = binding.InstrumentDisplayReccyclerView;
        repository = TuneTradeRepository.getRepository(getApplication());
        final InstrumentsAdapter adapter = new InstrumentsAdapter(new InstrumentsAdapter.instrumentDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        instrumentsViewModel.getAllLogsByCategory("Vinyls").observe(this, products -> {
            adapter.submitList(products);
        });
    }



    public static Intent VinylsIntentFactory(Context context, String USER_ID){
        Intent intent = new Intent(context, VinylsActivity.class);
        intent.putExtra(LANDING_PAGE_USER_ID, USER_ID);
        return intent;
    }
}