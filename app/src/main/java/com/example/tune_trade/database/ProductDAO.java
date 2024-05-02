package com.example.tune_trade.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.database.entities.User;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Delete
    void Delete(Product product);

    @Query("SELECT * from " + TuneTradeDatabase.productTable)
    List<Product> getAllRecords();

    @Query("SELECT * FROM " + TuneTradeDatabase.productTable + " WHERE category == :category")
    LiveData<Product> getProductsByCategory(String category);

    @Query("SELECT * FROM " + TuneTradeDatabase.productTable + " WHERE category == :category")
    LiveData<List<Product>> getProductsByCategoryLiveData(String category);

    @Query("SELECT * FROM " + TuneTradeDatabase.productTable + " WHERE category == :category")
    List<Product> getProductsByCategoryList(String category);
}
