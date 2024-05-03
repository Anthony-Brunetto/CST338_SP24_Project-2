package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityInstrumentsPageBinding;
import com.example.tune_trade.viewHolder.InstrumentsAdapter;
import com.example.tune_trade.viewHolder.InstrumentsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InstrumentsPageActivity extends AppCompatActivity implements InstrumentsAdapter.OnAddToCartClickListener{ // TODO: ADD BACK BUTTON OR GO BACK TO MAIN ACTIVITY


    private static final String instrument_userid =  "com.example.tune_trade.instrument_userid";

    ActivityInstrumentsPageBinding binding;
    private InstrumentsViewModel instrumentsViewModel;
    public static List<Product> productList = new ArrayList<>();

    int cartCount = 0;

    TuneTradeRepository repository;
    private int id;

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
        int userId = getIntent().getIntExtra(instrument_userid, -1);
        id = userId;
        final InstrumentsAdapter adapter = new InstrumentsAdapter(new InstrumentsAdapter.instrumentDiff());
        adapter.setOnAddToCartClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        instrumentsViewModel.getAllLogsByCategory("Instruments").observe(this, products -> {
            adapter.submitList(products);
        });

        repository.getAllProductsLiveData().observe(this, new Observer<List<Product>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Product> products) {
                productList.clear();
                productList.addAll(products);
                adapter.setProductList(productList);
                adapter.notifyDataSetChanged();
            }
        });

        repository.getCartCount(id).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                cartCount = Integer.parseInt(s);
            }
        });
    }

    @Override
    public void onAddToCartClick(int productId) {
        if(cartCount==0){
            Cart cart = new Cart(id);
            cart.setUserId(id);
            cart.setProducts(String.valueOf(productId));
            repository.insertCart(cart);
            Toast.makeText(this, "New Cart created and item added to cart", Toast.LENGTH_SHORT).show();
        }else {
            Cart cart1 = new Cart(id);
            cart1.setProducts(String.valueOf(productId));
            cart1.setUserId(id);
            repository.updateCart(cart1);
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        }
        
    }
    public static Intent InstrumentsPageIntentFactory(Context context, int USER_ID){
        Intent intent = new Intent(context, InstrumentsPageActivity.class);
        intent.putExtra(instrument_userid, USER_ID);
        return intent;
    }
}