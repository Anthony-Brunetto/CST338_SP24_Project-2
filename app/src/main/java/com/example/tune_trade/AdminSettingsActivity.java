package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityAdminSettingsBinding;

import java.util.Objects;

public class AdminSettingsActivity extends AppCompatActivity { // TODO: ADD BACK BUTTON OR GO BACK TO MAIN ACTIVITY
    private static final String LANDING_PAGE_USER_ID = "com.example.tune_trade.LANDING_PAGE_USER_ID";
    private TuneTradeRepository repository;
    ActivityAdminSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);
        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());


        binding.addAdminUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpPageActivity.SignUpPageIntentFactory(getApplicationContext(), true);
                startActivity(intent);
            }
        });

        binding.addDiscountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddDiscount.AddDiscountIntentFactory(getApplicationContext(), true, getIntent().getStringExtra(LANDING_PAGE_USER_ID));
                startActivity(intent);
            }
        });

        binding.removeProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteProductActivity.DeleteProductIntentFactory(getApplicationContext(), getIntent().getStringExtra(LANDING_PAGE_USER_ID));
                startActivity(intent);
            }
        });

        binding.addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddProductActivity.AddProductsIntentFactory(getApplicationContext(), getIntent().getStringExtra(LANDING_PAGE_USER_ID));
                startActivity(intent);

            }
        });
    }



    public static Intent AdminSettingPageIntentFactory(Context context, String USER_ID){
        Intent intent = new Intent(context, AdminSettingsActivity.class);
        intent.putExtra(LANDING_PAGE_USER_ID, USER_ID);
        return intent;
    }
}