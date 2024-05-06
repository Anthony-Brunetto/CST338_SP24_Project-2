package com.example.tune_trade;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.tune_trade.database.CartDAO;
import com.example.tune_trade.database.TuneTradeDatabase;
import com.example.tune_trade.database.UserDAO;
import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CartDAOTest {
    private CartDAO cartDAO;
    private UserDAO userDAO;
    private TuneTradeDatabase db;

    @Before
    public void createDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TuneTradeDatabase.class).build();
        cartDAO = db.cartDAO();
        userDAO = db.userDAO();
    }

    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void insertTest(){
        // Adding user to database for foreign key requirement
        User testUser = new User("test", "pass", "test");
        userDAO.insert(testUser);
        //  Creates new cart for us to pass in
        Cart test = new Cart(1);
        test.setProducts("1");
        //  Insert cart into database
        cartDAO.insert(test);
        //  Get list of carts from database
        List<Cart> allCarts = cartDAO.getAllRecords();
        //  Assert that list size is equal to 1
        assertEquals(1, allCarts.size());
        //  Assert that products are equal
        assertEquals(test.getProducts(), allCarts.get(0).getProducts());
    }


    @Test
    public void updateTest(){
        // Adding user to database for foreign key requirement
        User testUser = new User("test", "pass", "test");
        userDAO.insert(testUser);
        //  Creates new cart for us to pass in
        Cart test = new Cart(1);
        test.setProducts("1");
        //  Insert cart into database
        cartDAO.insert(test);
        //  Get list of carts from database
        List<Cart> allCarts = cartDAO.getAllRecords();
        //  Assert that list size is equal to 1
        assertEquals(1, allCarts.size());
        //  Assert that id is equal
        assertEquals(test.getProducts(), allCarts.get(0).getProducts());
        //  Update products in cart
        cartDAO.updateProductsByUserId("1", test.getUserId());
        //  Retrieve updated cart
        allCarts = cartDAO.getAllRecords();
        //  Assert that cart's product is "1"
        assertEquals("1", allCarts.get(0).getProducts());
    }

    @Test
    public void deleteTest(){
        // Adding user to database for foreign key requirement
        User testUser = new User("test", "pass", "test");
        userDAO.insert(testUser);
        //  Creates new cart for us to pass in
        Cart test = new Cart(1);
        test.setProducts("1");
        //  Insert cart into database
        cartDAO.insert(test);
        //  Get list of carts from database
        List<Cart> allCarts = cartDAO.getAllRecords();
        //  Assert that list size is equal to 1
        assertEquals(1, allCarts.size());
        //  Assert that id is equal
        assertEquals(test.getProducts(), allCarts.get(0).getProducts());
        //  Delete cart
        cartDAO.deleteAll();
        //  Retrieve all carts
        allCarts = cartDAO.getAllRecords();
        //  Assert that all carts list size is 0
        assertEquals(0, allCarts.size());
    }
}
