package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityLoginPageBinding;

public class LoginPageActivity extends AppCompatActivity {

    private TuneTradeRepository repository;

    private ActivityLoginPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());

        binding.attemptLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.goToSignUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpPageActivity.SignUpPageIntentFactory(getApplicationContext(), false);
                startActivity(intent);
            }
        });
    }

    private void verifyUser() {
        String username = binding.usernameTextBox.getText().toString();
        String password = binding.passwordTextBox.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginPageActivity.this, "Fields may not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    startActivity(LandingPage.landingPageIntentFactory(getApplicationContext(), user));
                } else {
                    Toast.makeText(LoginPageActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginPageActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent loginPageIntentFactory(Context context) {
        Intent intent = new Intent(context, LoginPageActivity.class);
        return intent;
    }
}