package com.example.tune_trade.viewHolder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tune_trade.database.TuneTradeRepository;
import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.database.entities.Product;

import java.util.List;

public class InstrumentsViewModel extends AndroidViewModel {
    private final TuneTradeRepository repository;
//    private final LiveData<List<Product>> allLogsByCategory;

    public InstrumentsViewModel(Application application){
        super(application);
        repository = TuneTradeRepository.getRepository(application);
//        allLogsByCategory = repository.getProductsByCategory(category);
    }

    public LiveData<List<Product>> getAllLogsByCategory(String category){
        return repository.getProductsByCategory(category);
    }

    public LiveData<List<Product>> getAllProductsLiveData(){
        return repository.getAllProductsLiveData();
    }

    public LiveData<String> getCartCount(int useriD){
        return repository.getCartCount(useriD);
    }

//    public void addProductToCart(int userId, Product product) {
//        // Check if a cart exists for the user
//        boolean cartExists = repository.checkCartExists(userId);
//
//        // If no cart exists, create a new cart for the user
//        if (!cartExists) {
//            Cart cart = new Cart(userId);
//            cart.setUserId(userId);
//            cart.setProducts(null);
//            repository.insertCart(cart);
//        }
//
//        // Add the product to the cart
//        repository.updateCart(userId, product);
//    }

    public void insert(Product log){
        repository.insertProduct(log);
    }
}
