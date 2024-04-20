package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityLandingPageBinding;

import java.util.Locale;

public class LandingPage extends AppCompatActivity {
    ActivityLandingPageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.LogoutButtonLandingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });





        
    }

    public static Intent landingPageIntentFactory(Context context, User user) {
        Intent intent = new Intent(context, LandingPage.class);
        return intent;
    }
}


