package com.example.tune_trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {

    private static final String LANDING_PAGE_USER_ID = "com.example.tune_trade.LANDING_PAGE_USER_ID";

    private TuneTradeRepository repository;

    private int userId;

    int tempcount;
    String tempname;

    ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.userId = getIntent().getIntExtra(LANDING_PAGE_USER_ID, -1);
        repository = TuneTradeRepository.getRepository(getApplication());
        updateDisplay();
        binding.checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });

    }

    private void checkout() {
        LiveData<String> cart_obj = repository.getProductsFromCartLong((long)userId);
        cart_obj.observe(this, products -> {
            if (products != null) {
                Log.i(MainActivity.TAG, "cart_string is not null: " + products);
                String[] cart_string_array = products.split(",");
                Log.i(MainActivity.TAG, "cart_string_array: " + cart_string_array);

                for (String elt : cart_string_array) {
                    LiveData<Product> product = repository.getProductsById(Integer.parseInt(elt));
                    product.observe(this, entry -> {
                        if (entry != null) {
                            Log.i(MainActivity.TAG, "entry num: " + entry.getCount() + "entry name: " + entry.getName());
                            int count = entry.getCount();
                            if (count == 1) {
                                count = 0;
                            } else {
                                count = count - 1;
                            }
                            //changed db
                        } else {
                            Log.i(MainActivity.TAG, "entry is null: " + userId);
                        }
                    });
                    repository.updateProductCountByName(tempcount - 1, tempname);
                }
            } else {
                Log.i(MainActivity.TAG, "cart_string is null: " + userId);
            }
        });
        repository.updateCartByUserId(null, userId);
    }


    private void updateDisplay() {
        binding.productTextView.setMovementMethod(new ScrollingMovementMethod());
        binding.productTextView.setText("");
        LiveData<String> cart_obj = repository.getProductsFromCartLong((long)userId);
        cart_obj.observe(this, products -> {
            if (products != null) {
                Log.i(MainActivity.TAG, "cart_string is not null: " + products);
                String[] cart_string_array = products.split(",");
                Log.i(MainActivity.TAG, "cart_string_array: " + cart_string_array);
                for (String elt : cart_string_array) {
                    LiveData<Product> product = repository.getProductsById(Integer.parseInt(elt));
                    product.observe(this, entry -> {
                        if (entry != null) {
                            String current = String.format(
                                    "Name = %s%nPrice = $%s%nDescription = %s%nCategory = %s%n%n%n",
                                    entry.getName(),
                                    entry.getPrice(),
                                    entry.getDescription(),
                                    entry.getCategory(),
                                    tempname = entry.getName(),
                                    tempcount = entry.getCount()
                            );
                            binding.productTextView.append(current);
                        } else {
                            Log.i(MainActivity.TAG, "entry is null: " + userId);
                        }
                    });
                }
            } else {
                Log.i(MainActivity.TAG, "cart_string is null: " + userId);
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