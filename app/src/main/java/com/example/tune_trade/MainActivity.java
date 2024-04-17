package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.example.tune_trade.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.goToLoginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginPageActivity.loginPageIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.goToSignUpPageButton.setOnClickListener(new View.OnClickListener() {
            // TODO: implement onClick to open sign up page
            @Override
            public void onClick(View v) {

            }
        });
    }
}