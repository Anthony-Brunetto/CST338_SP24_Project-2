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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertByID(Cart cart);
    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM " + TuneTradeDatabase.CART_TABLE + " WHERE userId == :userId")
    LiveData<Cart> getCartByUserId(String userId);

    @Query(" SELECT products FROM " + TuneTradeDatabase.CART_TABLE + " WHERE userId == :userId")
    LiveData<String> getProductsFromCart(String userId);

    @Query(" SELECT products FROM " + TuneTradeDatabase.CART_TABLE + " WHERE userId == :userId")
    LiveData<String> getProductsFromCartLong(long userId);


    @Query("UPDATE " + TuneTradeDatabase.CART_TABLE + " SET products = :products WHERE userId == :userId")
    void updateProductsByUserId(String products, long userId);

    @Query("UPDATE " + TuneTradeDatabase.CART_TABLE + " SET products = CASE WHEN products IS NULL THEN :productId ELSE products || ',' || :productId END WHERE userId = :userId")
    void addProductToCart(String productId, long userId);


    @Query("DELETE FROM " + TuneTradeDatabase.CART_TABLE)
    void deleteAll();

    @Query("SELECT COUNT(*) FROM " + TuneTradeDatabase.CART_TABLE + " WHERE userId == :userId")
    LiveData<String> getCartCount(int userId);


}
