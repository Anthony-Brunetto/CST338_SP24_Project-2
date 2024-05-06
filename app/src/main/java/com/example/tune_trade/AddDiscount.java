package com.example.tune_trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.databinding.ActivityAddDiscountBinding;

public class AddDiscount extends AppCompatActivity { // TODO: ADD BACK BUTTON OR GO BACK TO ADMIN SETTINGS ACTIVITY
    private static final String LANDING_PAGE_USER_ID = "com.example.tune_trade.LANDING_PAGE_USER_ID";

    private TuneTradeRepository repository;

    ActivityAddDiscountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDiscountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());

        binding.addDiscountToProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDiscountToProduct();
            }
        });

    }

    private void addGlobalDiscount() {
//        String discount = binding.enterGlobalDiscountTextView.getText().toString();
//        double i_discount;
//        if (discount.isEmpty()) {
//            Toast.makeText(AddDiscount.this, "Discount Field must not be blank", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        i_discount = (double) Integer.parseInt(discount) / 100;
//        Log.i(MainActivity.TAG, String.valueOf(i_discount));
//        if (repository.updateProductDiscountByName(i_discount, "GLOBAL DISCOUNT")) {
//            Toast.makeText(AddDiscount.this, "Product discount updated!", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(AddDiscount.this, "Problem with updating product discount", Toast.LENGTH_SHORT).show();
//        }
    }

    private void addDiscountToProduct() {
        String productName = binding.nameProductNameTextView.getText().toString();
        String discount = binding.percentageDiscountTextView.getText().toString();
        double i_discount;
        if (productName.isEmpty()) {
            Toast.makeText(AddDiscount.this, "Product Field must not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (productName.equals("GLOBAL DISCOUNT")) {
            Toast.makeText(AddDiscount.this, "Invalid product name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (discount.isEmpty()) {
            Toast.makeText(AddDiscount.this, "Discount Field must not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        i_discount = (double) Integer.parseInt(discount) / 100;
        Log.i(MainActivity.TAG, productName);
        if (repository.updateProductDiscountByName(i_discount, productName)) {
            Toast.makeText(AddDiscount.this, "Product discount updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddDiscount.this, "Problem with updating product discount", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent AddDiscountIntentFactory(Context context, boolean isAdmin, String USER_ID) {
        if (isAdmin) {
            Intent intent = new Intent(context, AddDiscount.class);
            intent.putExtra(LANDING_PAGE_USER_ID, USER_ID);
            return intent;
        }
        return null;
    }
}