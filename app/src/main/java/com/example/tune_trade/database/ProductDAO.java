package com.example.tune_trade.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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

    @Query("SELECT * from " + TuneTradeDatabase.productTable)
    List<Product> getAllRecords();

//    @Query("SELECT * FROM " + TuneTradeDatabase.productTable + " WHERE category == :category")
//    LiveData<User> getProductByCategory(String category);
}
