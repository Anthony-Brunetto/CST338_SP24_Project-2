package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tune_trade.databinding.ActivityLoginPageBinding;

public class LoginPageActivity extends AppCompatActivity {

    private ActivityLoginPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.attemptLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.usernameTextBox.getText().toString();
                String password = binding.passwordTextBox.getText().toString();
                // TODO: Validate user/pass w/ db
            }
        });
    }

    public static Intent loginPageIntentFactory(Context context) {
        Intent intent = new Intent(context, LoginPageActivity.class);
        return intent;
    }
}