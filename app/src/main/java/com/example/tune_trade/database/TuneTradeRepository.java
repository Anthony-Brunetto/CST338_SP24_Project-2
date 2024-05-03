package com.example.tune_trade.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.MainActivity;
import com.example.tune_trade.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TuneTradeRepository {
    private final ProductDAO productDAO;

    private final UserDAO userDAO;

    private final CartDAO cartDAO;

    private ArrayList<Product> allLogs;

    private static TuneTradeRepository repository;

    private TuneTradeRepository(Application application) {
        TuneTradeDatabase db = TuneTradeDatabase.getDatabase(application);
        this.productDAO = db.productDAO();
        this.userDAO = db.userDAO();
        this.cartDAO = db.cartDAO();
        this.allLogs = (ArrayList<Product>) this.productDAO.getAllRecords();
    }

    public static TuneTradeRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<TuneTradeRepository> future = TuneTradeDatabase.databaseWriteExecutor.submit(
                new Callable<TuneTradeRepository>() {
                    @Override
                    public TuneTradeRepository call() throws Exception {
                        repository = new TuneTradeRepository(application);
                        return repository;
                    }
                }
        );

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting TuneTradeRepository, thread error.");
        }
        return null;
    }

    public ArrayList<Product> getAllProducts() { // was getAllLogs
        Future<ArrayList<Product>> future = TuneTradeDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Product>>() {
                    @Override
                    public ArrayList<Product> call() throws Exception {
                        return (ArrayList<Product>) productDAO.getAllRecords();
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

    public LiveData<List<Product>> getProductsByCategory(String category) {
        return productDAO.getProductsByCategoryLiveData(category);
    }

    public void insertUser(User user) {
        TuneTradeDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }

    public void insertCart(Cart cart) {
        TuneTradeDatabase.databaseWriteExecutor.execute(()-> {
            cartDAO.insert(cart);
        });
    }

    public void updateCart(Cart cart){
        TuneTradeDatabase.databaseWriteExecutor.execute(()->{
            cartDAO.updateProductsByUserId(cart.getProducts(), cart.getUserId());
        });
    }

    public LiveData<List<Product>> getAllProductsLiveData(){
        return productDAO.getAllRecordsLiveData();
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<User> getUserByIsAdmin(boolean isAdmin) {
        return userDAO.getUserByIsAdmin(isAdmin);
    }

    public void deleteProductByName(String name){
        TuneTradeDatabase.databaseWriteExecutor.execute(()->{
            productDAO.deleteProductByName(name);
        });
    }

    public LiveData<Product> getProductByProductName(String username) {
        return productDAO.getProductsByProductName(username);
    }

    public boolean updateProductDiscountByName(double discount, String name) {
        try {
            TuneTradeDatabase.databaseWriteExecutor.execute(() -> {
                productDAO.updateProductDiscountByName(discount, name);
            });
        } catch (Exception e) {
            Log.i(MainActivity.TAG, e.toString());
            return false;
        }
        return true;
    }
}
