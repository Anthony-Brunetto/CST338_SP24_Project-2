package com.example.tune_trade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.database.entities.User;
import com.example.tune_trade.databinding.ActivityInstrumentsPageBinding;
import com.example.tune_trade.databinding.ActivityVinylsBinding;
import com.example.tune_trade.viewHolder.InstrumentsAdapter;
import com.example.tune_trade.viewHolder.InstrumentsViewModel;

import java.util.ArrayList;
import java.util.List;

public class VinylsActivity extends AppCompatActivity implements InstrumentsAdapter.OnAddToCartClickListener { // TODO: ADD BACK BUTTON OR GO BACK TO MAIN ACTIVITY

    private TuneTradeRepository repository;
    ActivityVinylsBinding binding;
    private InstrumentsViewModel instrumentsViewModel;

    public static List<Product> productList = new ArrayList<>();

    private static final String vinyls_userid = "com.example.tune_trade.vinyls_userid";

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinyls);
        binding = ActivityVinylsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        instrumentsViewModel = new ViewModelProvider(this).get(InstrumentsViewModel.class);

        RecyclerView recyclerView = binding.InstrumentDisplayReccyclerView;
        repository = TuneTradeRepository.getRepository(getApplication());
        int userId = getIntent().getIntExtra(vinyls_userid, -1);
        id = userId;
        final InstrumentsAdapter adapter = new InstrumentsAdapter(new InstrumentsAdapter.instrumentDiff());
        adapter.setOnAddToCartClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        instrumentsViewModel.getAllLogsByCategory("Vinyls").observe(this, products -> {
            adapter.submitList(products);
        });

        repository.getAllProductsLiveData().observe(this, new Observer<List<Product>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Product> products) {
                productList.clear();
                productList.addAll(products);
                adapter.setProductList(productList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAddToCartClick(int productId) {
        Cart cart1 = new Cart(id);
        cart1.setProducts(String.valueOf(productId));
        cart1.setUserId(id);
        repository.updateCart(cart1);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
    }

    public static Intent VinylsIntentFactory(Context context, int USER_ID){
        Intent intent = new Intent(context, VinylsActivity.class);
        intent.putExtra(vinyls_userid, USER_ID);
        return intent;
    }
}