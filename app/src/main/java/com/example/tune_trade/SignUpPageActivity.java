package com.example.tune_trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.UserDAO;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivitySignUpPageBinding;

public class SignUpPageActivity extends AppCompatActivity {

    private TuneTradeRepository repository;
    ActivitySignUpPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);

            }
        });
    }

    private void signup(){
        String username = binding.usernameSignupTextBox.getText().toString();
        String password = binding.passwordSignupTextBox.getText().toString();
        String Address = binding.addressSignupTextBox.getText().toString();

        if (username.isEmpty() || password.isEmpty() || Address.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, password, Address);
        user.setUsername(username);
        user.setPassword(password);
        user.setAddress(Address);
        repository.insertUser(user);

        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();


    }

    public static Intent SignUpPageIntentFactory(Context context){
        Intent intent = new Intent(context, SignUpPageActivity.class);
        return intent;
    }
}