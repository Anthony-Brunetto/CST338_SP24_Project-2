package com.example.tune_trade;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.tune_trade.database.ProductDAO;
import com.example.tune_trade.database.TuneTradeDatabase;
import com.example.tune_trade.database.entities.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductDAOTest {
    private ProductDAO productDAO;
    private TuneTradeDatabase db;

    @Before
    public void createDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TuneTradeDatabase.class).build();
        productDAO = db.productDAO();
    }

    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void insertTest(){
        //  Creates new product for us to pass in and a required product for functionality
        Product global = new Product ("GLOBAL", -1, -1, "GLOBAL", "GLOBAL");
        Product test = new Product ("test", -1, -1, "this is a test", "test");
        //  Insert products into database
        productDAO.insert(global);
        productDAO.insert(test);
        //  Get list of products from database
        List<Product> allProducts = productDAO.getAllRecords();
        //  Assert that list size is equal to 1
        assertEquals(1, allProducts.size());
        //  Assert that name is equal
        assertEquals(test.getName(), allProducts.get(0).getName());
    }


    @Test
    public void updateTest(){
        //  Creates new product for us to pass in and a required product for functionality
        Product global = new Product ("GLOBAL", -1, -1, "GLOBAL", "GLOBAL");
        Product test = new Product ("test", -1, -1, "this is a test", "test");
        //  Inserts products into database
        productDAO.insert(global);
        productDAO.insert(test);
        //  Retrieves list of products from the database
        List<Product> allProducts = productDAO.getAllRecords();
        //  Assert that list size is equal to 1
        assertEquals(1, allProducts.size());
        //  Assert that names are equal
        assertEquals(test.getCount(), allProducts.get(0).getCount());
        //  Update product count in database
        productDAO.updateProductCountByName(1, "test");
        //  Retrieve users from database (our user with updated username)
        allProducts = productDAO.getAllRecords();
        //  Assert that product's new count is 1
        assertEquals(1, allProducts.get(0).getCount());
    }

    @Test
    public void deleteTest(){
        //  Creates new product for us to pass in and a required product for functionality
        Product global = new Product ("GLOBAL", -1, -1, "GLOBAL", "GLOBAL");
        Product test = new Product ("test", -1, -1, "this is a test", "test");
        //  Inserts products into database
        productDAO.insert(global);
        productDAO.insert(test);
        //  Retrieves list of products from the database
        List<Product> allProducts = productDAO.getAllRecords();
        //  Assert that list size is equal to 1
        assertEquals(1, allProducts.size());
        //  Assert that names are equal
        assertEquals(test.getCount(), allProducts.get(0).getCount());
        //  Delete product
        productDAO.deleteProductByName("test");
        //  Retrieve all users (should be none inside since we deleted the only one)
        allProducts = productDAO.getAllRecords();
        //  Assert that all users list size is 0
        assertEquals(0, allProducts.size());
    }
}
