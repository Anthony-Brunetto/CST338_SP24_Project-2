package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Product;

public class DeleteProductActivity extends AppCompatActivity {

    private TuneTradeRepository repository;
    private boolean nameChecked = false;
    com.example.tune_trade.databinding.ActivityDeleteProductAcitivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product_acitivity);
        binding = com.example.tune_trade.databinding.ActivityDeleteProductAcitivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        repository = TuneTradeRepository.getRepository(getApplication());

        binding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    private void deleteProduct() {
        if(nameChecked){
            return;
        }
        String name = binding.productNameDeleteTextView.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(this, "Field cannot be left blank", Toast.LENGTH_SHORT).show();
        }

        repository.getProductByProductName(name).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if(product == null){
                    if(nameChecked){
                        return;
                    }
                    Toast.makeText(DeleteProductActivity.this, "Product does not exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    repository.deleteProductByName(name);
                    nameChecked = true;

                    Toast.makeText(DeleteProductActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = AdminSettingsActivity.AdminSettingPageIntentFactory(getApplicationContext());
                    startActivity(intent);

                }
            }
        });



    }

    public static Intent DeleteProductIntentFactory(Context context){
        Intent intent = new Intent(context, DeleteProductActivity.class);
        return intent;
    }
}