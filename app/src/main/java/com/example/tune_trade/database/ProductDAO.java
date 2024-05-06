package com.example.tune_trade.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tune_trade.database.entities.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Query("SELECT * from " + TuneTradeDatabase.PRODUCT_TABLE + " LIMIT -1 OFFSET 1")
    List<Product> getAllRecords();

    @Query("SELECT * from " + TuneTradeDatabase.PRODUCT_TABLE + " LIMIT -1 OFFSET 1")
    LiveData<List<Product>> getAllRecordsLiveData();

    @Query("SELECT * FROM " + TuneTradeDatabase.PRODUCT_TABLE + " WHERE category == :category")
    LiveData<Product> getProductsByCategory(String category);

    @Query("SELECT * FROM " + TuneTradeDatabase.PRODUCT_TABLE + " WHERE category == :category")
    LiveData<List<Product>> getProductsByCategoryLiveData(String category);

    @Query("SELECT * FROM " + TuneTradeDatabase.PRODUCT_TABLE + " WHERE category == :category")
    List<Product> getProductsByCategoryList(String category);

    @Query("DELETE FROM " + TuneTradeDatabase.PRODUCT_TABLE)
    void deleteAll();

    @Query("DELETE FROM " + TuneTradeDatabase.PRODUCT_TABLE + " WHERE name == :name")
    void deleteProductByName(String name);

    @Query("SELECT * FROM " + TuneTradeDatabase.PRODUCT_TABLE + " WHERE name == :name")
    LiveData<Product> getProductsByProductName(String name);

    @Query("SELECT * FROM " + TuneTradeDatabase.PRODUCT_TABLE + " WHERE id == :id")
    LiveData<Product> getProductsById(int id);

    @Query("UPDATE " + TuneTradeDatabase.PRODUCT_TABLE + " SET discount = :discount WHERE name == :name")
    void updateProductDiscountByName(double discount, String name);

    @Query("UPDATE " + TuneTradeDatabase.PRODUCT_TABLE + " SET count = :count WHERE name == :name")
    void updateProductCountByName(int count, String name);
}
