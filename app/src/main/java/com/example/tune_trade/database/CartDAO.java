package com.example.tune_trade.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tune_trade.database.entities.Cart;

@Dao
public interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM " + TuneTradeDatabase.CART_TABLE + " WHERE userId == :userId")
    LiveData<Cart> getCartByUserId(String userId);

    @Query("UPDATE " + TuneTradeDatabase.CART_TABLE + " SET products = :products WHERE userId == :userId")
    void updateProductsByUserId(String products, String userId);

    @Query("DELETE FROM " + TuneTradeDatabase.CART_TABLE)
    void deleteAll();
}