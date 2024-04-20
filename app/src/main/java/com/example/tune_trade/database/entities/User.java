package com.example.tune_trade.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tune_trade.database.TuneTradeDatabase;

@Entity(tableName = TuneTradeDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    private String password;

    private boolean isAdmin;

    private String address;

    public User(String username, String password, String address) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.isAdmin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
