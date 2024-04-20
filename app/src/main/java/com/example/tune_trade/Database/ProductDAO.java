package com.example.tune_trade.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tune_trade.Database.entities.Product;

import java.util.ArrayList;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Query("SELECT * from " + TuneTradeDatabase.productTable)
    ArrayList<Product> getAllRecords();
}
