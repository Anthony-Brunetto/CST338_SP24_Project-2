package com.example.tune_trade.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tune_trade.database.TuneTradeDatabase;

import java.util.Objects;

@Entity(tableName = TuneTradeDatabase.productTable)
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int count;

    private double price;

    private String description;

    private String category;

    public Product(String name, int count, double price, String description, String category) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && count == product.count && Double.compare(price, product.price) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, count, price, description, category);
    }
}
