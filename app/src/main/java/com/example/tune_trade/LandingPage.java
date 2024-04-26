package com.example.tune_trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityLandingPageBinding;

import java.util.Objects;

public class LandingPage extends AppCompatActivity {
    private static final String LANDING_PAGE_USER_ID = "com.example.tune_trade.LANDING_PAGE_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.tune_trade.SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.tune_trade.SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;
    private static final String LANDING_PAGE_USERNAME = "com.example.tune_trade.LANDING_PAGE_USERNAME";
    static final String SHARED_PREFERENCE_USERNAME_KEY = "com.example.tune_trade.SHARED_PREFERENCE_USERNAME_KEY";
    static final String SHARED_PREFERENCE_USERNAME_VALUE = "com.example.tune_trade.SHARED_PREFERENCE_USERNAME_VALUE";
    private static final String LANDING_PAGE_IS_ADMIN = "com.example.tune_trade.LANDING_PAGE_IS_ADMIN";
    static final String SHARED_PREFERENCE_IS_ADMIN_KEY = "com.example.tune_trade.SHARED_PREFERENCE_IS_ADMIN_KEY";
    static final String SHARED_PREFERENCE_IS_ADMIN_VALUE = "com.example.tune_trade.SHARED_PREFERENCE_IS_ADMIN_VALUE";
    private TuneTradeRepository repository;
    ActivityLandingPageBinding binding;

    private int loggedInUserId = -1;
    private String loggedInUserName;
    private boolean loggedInIsAdmin;
    private User user;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialogue();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialogue() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandingPage.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("LOGOUT?");

        alertBuilder.setPositiveButton("LOGOUT?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                logout();
            }
        });
        alertBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        sharedPrefEditor.apply();
        getIntent().putExtra(LANDING_PAGE_USER_ID, LOGGED_OUT);
        startActivity(LoginPageActivity.loginPageIntentFactory(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());
        loginUser();
        getUsernameFromSharedPreferences();
        getIsAdminFromSharedPreferences();
        if(loggedInUserId == -1) {
            Intent intent = LoginPageActivity.loginPageIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        if(Objects.equals(loggedInUserName, "")){
            Intent intent = LoginPageActivity.loginPageIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        binding.WelcomeTextTextview.setText("Welcome " + loggedInUserName);
        if(loggedInIsAdmin){
            binding.AdminButtonLandingPage.setVisibility(View.VISIBLE);
        }
        else{
            binding.AdminButtonLandingPage.setVisibility(View.INVISIBLE);
        }
        binding.LogoutButtonLandingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.AdminButtonLandingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminSettingsActivity.AdminSettingPageIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private void getUsernameFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERNAME_KEY, Context.MODE_PRIVATE);
        loggedInUserName = sharedPreferences.getString(SHARED_PREFERENCE_USERID_VALUE, "");
        if(!loggedInUserName.isEmpty()){
            return;
        }
        loggedInUserName = getIntent().getStringExtra(LANDING_PAGE_USERNAME);
        if(Objects.equals(loggedInUserName, "")){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(loggedInUserName);
        userObserver.observe(this, user -> {
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
    }

    private void getIsAdminFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_IS_ADMIN_KEY, Context.MODE_PRIVATE);
        loggedInIsAdmin = sharedPreferences.getBoolean(SHARED_PREFERENCE_IS_ADMIN_VALUE, false);
        if(loggedInIsAdmin){
            return;
        }
        loggedInIsAdmin = getIntent().getBooleanExtra(LANDING_PAGE_IS_ADMIN, false);
        if(!loggedInIsAdmin){
            return;
        }
        LiveData<User> userObserver = repository.getUserByIsAdmin(loggedInIsAdmin);
        userObserver.observe(this, user -> {
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
    }


    private void loginUser() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);

        if(loggedInUserId != LOGGED_OUT){
            return;
        }
        loggedInUserId = getIntent().getIntExtra(LANDING_PAGE_USER_ID, -1);
        if(loggedInUserId == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
    }

    public static Intent landingPageIntentFactory(Context context, User user) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(LANDING_PAGE_USER_ID, user.getId());
        intent.putExtra(LANDING_PAGE_USERNAME, user.getUsername());
        intent.putExtra(LANDING_PAGE_IS_ADMIN, user.isAdmin());
        return intent;
    }
}


