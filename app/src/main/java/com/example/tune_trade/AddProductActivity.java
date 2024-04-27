package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.databinding.ActivityAddProductBinding;

public class AddProductActivity extends AppCompatActivity {
    private TuneTradeRepository repository;
    ActivityAddProductBinding binding;
    private RadioGroup categoryRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TuneTradeRepository.getRepository(getApplication());

        binding.addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    private void addProduct(){
        String name = binding.nameAddProductTextBox.getText().toString();
        int count = Integer.parseInt(binding.countAddProductTextBox.getText().toString());
        double price = Double.parseDouble(binding.priceAddProductTextBox.getText().toString());
        String description = binding.descriptionAddProductTextBox.getText().toString();
        RadioButton selectedRadioButton = findViewById(binding.radioGroup.getCheckedRadioButtonId());
        String category = selectedRadioButton.getText().toString();

        if(name.isEmpty() || count == 0 || price == 0 || description.isEmpty() || category.isEmpty() ){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }

        Product product = new Product(name, count, price, description, category);
        product.setName(name);
        product.setCategory(category);
        product.setCount(count);
        product.setPrice(price);
        product.setDescription(description);

        repository.insertProduct(product);

        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
        Intent intent = AdminSettingsActivity.AdminSettingPageIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    public static Intent AddProductsIntentFactory(Context context){
        Intent intent = new Intent(context, AddProductActivity.class);
        return intent;
    }
}