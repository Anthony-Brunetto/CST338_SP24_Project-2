package com.example.tune_trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.databinding.ActivityCartBinding;

import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    private static final String LANDING_PAGE_USER_ID = "com.example.tune_trade.LANDING_PAGE_USER_ID";

    private TuneTradeRepository repository;

    ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());
        long userId = getIntent().getIntExtra(LANDING_PAGE_USER_ID, -1);
        LiveData<String> cart_string = repository.getProductsFromCartLong(userId);
        if (cart_string.getValue() != null) {
            String[] cart_string_array = cart_string.getValue().split(",");
        } else {
            Log.i(MainActivity.TAG, "cart_string is null: " + userId);
        }
//        Log.i(MainActivity.TAG, cart_string_array.toString());

        binding.checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public static Intent CartActivityIntentFactory(Context context, int USER_ID) {
        Intent intent = new Intent(context, CartActivity.class);
        Log.i(MainActivity.TAG, "Userid: " + USER_ID);
        intent.putExtra(LANDING_PAGE_USER_ID, USER_ID);
        return intent;
    }
}