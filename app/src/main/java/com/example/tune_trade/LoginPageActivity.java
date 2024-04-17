package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tune_trade.databinding.ActivityLoginPageBinding;
import com.example.tune_trade.databinding.ActivityMainBinding;

public class LoginPageActivity extends AppCompatActivity {

    private ActivityLoginPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public static Intent loginPageIntentFactory(Context context) {
        Intent intent = new Intent(context, LoginPageActivity.class);
        return intent;
    }
}