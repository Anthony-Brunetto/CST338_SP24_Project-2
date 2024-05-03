package com.example.tune_trade.viewHolder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tune_trade.database.TuneTradeRepository;
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

    public void insert(Product log){
        repository.insertProduct(log);
    }
}
