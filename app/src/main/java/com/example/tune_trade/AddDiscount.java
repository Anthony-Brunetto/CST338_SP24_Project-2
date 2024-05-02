package com.example.tune_trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityAddDiscountBinding;
import com.example.tune_trade.databinding.ActivityMainBinding;

public class AddDiscount extends AppCompatActivity {

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

    private void addDiscountToProduct() {
        String productName = binding.nameProductNameTextView.getText().toString();
        String discount = binding.percentageDiscountTextView.getText().toString();
        int i_discount;
        if (productName.isEmpty()) {
            Toast.makeText(AddDiscount.this, "Product Field must not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (discount.isEmpty()) {
            Toast.makeText(AddDiscount.this, "Discount Field must not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        i_discount = Integer.parseInt(discount) / 100;
        Log.i(MainActivity.TAG, productName);
        if (repository.updateProductDiscountByName(i_discount, productName)) {
            Toast.makeText(AddDiscount.this, "Product discount updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddDiscount.this, "Problem with updating product discount", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent AddDiscountIntentFactory(Context context, boolean isAdmin) {
        if (isAdmin) {
            Intent intent = new Intent(context, AddDiscount.class);
            return intent;
        }
        return null;
    }
}