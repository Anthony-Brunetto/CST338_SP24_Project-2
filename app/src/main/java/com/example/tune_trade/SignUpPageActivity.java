package com.example.tune_trade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivitySignUpPageBinding;

public class SignUpPageActivity extends AppCompatActivity {

    private TuneTradeRepository repository;
    ActivitySignUpPageBinding binding;
    private boolean usernameChecked = false;
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
//                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
//                startActivity(intent);


            }
        });
    }

    private void signup() {
        if(usernameChecked){
            return;
        }
        String username = binding.usernameSignupTextBox.getText().toString();
        String password = binding.passwordSignupTextBox.getText().toString();
        String re_enter_password = binding.reEnterPasswordSignupTextBox.getText().toString();
        String Address = binding.addressSignupTextBox.getText().toString();


        if (username.isEmpty() || password.isEmpty() || Address.isEmpty()) {

            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!password.equals(re_enter_password)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();
            return;
        }
        repository.getUserByUsername(username).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                    if (user != null) {
                        if(usernameChecked){
                            return;
                        }
                        Log.d("UserRepository", "User found: " + user.getUsername());
                        Toast.makeText(SignUpPageActivity.this, "Username is already taken, please choose a different username", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("UserRepository", "User not found");
                        User user1 = new User(username, password, Address);
                        user1.setUsername(username);
                        user1.setPassword(password);
                        user1.setAddress(Address);
                        repository.insertUser(user1);
                        usernameChecked = true;
                        Toast.makeText(SignUpPageActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                        Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                        startActivity(intent);
                    }
            }

        });
    }

    public static Intent SignUpPageIntentFactory(Context context){
        Intent intent = new Intent(context, SignUpPageActivity.class);
        return intent;
    }
}