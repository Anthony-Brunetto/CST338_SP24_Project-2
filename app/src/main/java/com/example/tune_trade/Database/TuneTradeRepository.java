package com.example.tune_trade.Database;

import android.app.Application;
import android.util.Log;

import com.example.tune_trade.Database.entities.Product;
import com.example.tune_trade.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TuneTradeRepository {
    private ProductDAO productDAO;

    private ArrayList<Product> allLogs;

    public TuneTradeRepository(Application application) {
        TuneTradeDatabase db = TuneTradeDatabase.getDatabase(application);
        this.productDAO = db.productDAO();
        this.allLogs = this.productDAO.getAllRecords();
    }

    public ArrayList<Product> getAllLogs() {
        Future<ArrayList<Product>> future = TuneTradeDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Product>>() {
                    @Override
                    public ArrayList<Product> call() throws Exception {
                        return productDAO.getAllRecords();
                    }
                }
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all Products in repository");
        }
        return null;
    }

    public void insertProduct(Product product) {
        TuneTradeDatabase.databaseWriteExecutor.execute(()->{
            productDAO.insert(product);
        });
    }
}
