package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TuneTradeRepository repository;

    public static final String TAG = "DAC_TUNE_TRADE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());

        binding.goToLoginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginPageActivity.loginPageIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.goToSignUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpPageActivity.SignUpPageIntentFactory(getApplicationContext(),false);
                startActivity(intent);
            }
        });
    }

    public static Intent MainActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, LoginPageActivity.class);
        return intent;
    }
}