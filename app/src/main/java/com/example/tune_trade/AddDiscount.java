package com.example.tune_trade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.tune_trade.database.TuneTradeRepository;
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
        String product = binding.addDiscountToProductButton.getText().toString();
        String discount = binding.percentageDiscountTextView.getText().toString();
        int i_discount;
        if (product.isEmpty()) {
            Toast.makeText(AddDiscount.this, "Product Field must not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (discount.isEmpty()) {
            Toast.makeText(AddDiscount.this, "Discount Field must not be blank", Toast.LENGTH_SHORT).show();
            return;
        } else {
            i_discount = Integer.parseInt(discount);
        }
        LiveData<User> productObserver = repository.getProductByProductName();
        // TODO: CONTINUE
    }
}