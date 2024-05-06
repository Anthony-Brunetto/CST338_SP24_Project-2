package com.example.tune_trade.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tune_trade.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + TuneTradeDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM " + TuneTradeDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsersList();

    @Query("DELETE from " + TuneTradeDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + TuneTradeDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + TuneTradeDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("SELECT * FROM " + TuneTradeDatabase.USER_TABLE + " WHERE isAdmin == :isAdmin")
    LiveData<User> getUserByIsAdmin(boolean isAdmin);

}
