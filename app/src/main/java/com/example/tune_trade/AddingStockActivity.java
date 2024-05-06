package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.databinding.ActivityAddingStockBinding;
import com.example.tune_trade.viewHolder.InstrumentsViewModel;

public class AddingStockActivity extends AppCompatActivity {

    private static final String LANDING_PAGE_USER_ID = "com.example.tune_trade.LANDING_PAGE_USER_ID";
    private TuneTradeRepository repository;
    ActivityAddingStockBinding binding;
    private boolean nameChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_stock);
        binding = ActivityAddingStockBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        repository = TuneTradeRepository.getRepository(getApplication());
        binding.restockProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restock();
            }
        });
    }

    private void restock() {
        if (nameChecked) {
            return;
        }

        String name = binding.productNameDeleteTextView.getText().toString();
        int count = Integer.parseInt(binding.productAmountRestockTextView.getText().toString());

        if (name.isEmpty()) {
            Toast.makeText(this, "Field cannot be left blank", Toast.LENGTH_SHORT).show();
            return;
        }

        repository.getProductByProductName(name).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product == null) {
                    if (nameChecked) {
                        return;
                    }
                    Toast.makeText(AddingStockActivity.this, "Product does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    repository.updateProductCountByName(count, name);
                    nameChecked = true;
                    Toast.makeText(AddingStockActivity.this, "Product Restocked", Toast.LENGTH_SHORT).show();
                    Intent intent = AdminSettingsActivity.AdminSettingPageIntentFactory(getApplicationContext(), getIntent().getStringExtra(LANDING_PAGE_USER_ID));
                    startActivity(intent);
                    repository.getProductByProductName(name).removeObserver(this);
                }
            }
        });
    }



    public static Intent AddingStockIntentfactory(Context context, String USER_ID){
        Intent intent = new Intent(context, AddingStockActivity.class);
        intent.putExtra(LANDING_PAGE_USER_ID, USER_ID);
        return intent;
    }
}