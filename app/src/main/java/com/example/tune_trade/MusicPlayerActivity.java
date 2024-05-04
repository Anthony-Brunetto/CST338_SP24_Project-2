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
import com.example.tune_trade.databinding.ActivityMusicPlayerBinding;
import com.example.tune_trade.viewHolder.InstrumentsAdapter;
import com.example.tune_trade.viewHolder.InstrumentsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPlayerActivity extends AppCompatActivity implements InstrumentsAdapter.OnAddToCartClickListener { // TODO: ADD BACK BUTTON OR GO BACK TO MAIN SETTINGS ACTIVITY
    private TuneTradeRepository repository;
    ActivityMusicPlayerBinding binding;
    private InstrumentsViewModel instrumentsViewModel;
    private static final String musicplayers_userid = "com.example.tune_trade.musicplayers_userid";

    int cartCount = 0;
    public static List<Product> productList = new ArrayList<>();
    private String productsCart;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        binding = ActivityMusicPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        instrumentsViewModel = new ViewModelProvider(this).get(InstrumentsViewModel.class);

        RecyclerView recyclerView = binding.InstrumentDisplayReccyclerView;
        repository = TuneTradeRepository.getRepository(getApplication());
        int userId = getIntent().getIntExtra(musicplayers_userid, -1);
        id = userId;
//        products = repository.getProductsCart(String.valueOf(id));
        final InstrumentsAdapter adapter = new InstrumentsAdapter(new InstrumentsAdapter.instrumentDiff());
        adapter.setOnAddToCartClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        instrumentsViewModel.getAllLogsByCategory("Music Players").observe(this, products -> {
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

        repository.getCartCount(id).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                cartCount = Integer.parseInt(s);
            }
        });

        instrumentsViewModel.getProductsCart(String.valueOf(id)).observe(this, products ->{
            productsCart = products;
        });

    }

    @Override
    public void onAddToCartClick(int productId) {
//        products = String.valueOf(repository.getProductsCart(String.valueOf(id)));
        StringBuilder sb = new StringBuilder();
        if(cartCount == 0){
            Cart cart = new Cart(id);
            cart.setUserId(id);
            cart.setProducts(Collections.singletonList(String.valueOf(productId)).toString());
            repository.insertCart(cart);
            Toast.makeText(this, "New Cart created and item added to cart", Toast.LENGTH_SHORT).show();
        }else {
//            if(productsCart.isEmpty()){
//                productsCart = "";
//            }
            sb.append(productsCart).append(",");
            sb.append(productId);
            Cart cart1 = new Cart(id);
            cart1.setProducts(String.valueOf(sb));
            cart1.setUserId(id);
            repository.updateCart(cart1);
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent MusicPlayersIntentFactory(Context context, int USER_ID){
        Intent intent = new Intent(context, MusicPlayerActivity.class);
        intent.putExtra(musicplayers_userid, USER_ID);
        return intent;
    }
}