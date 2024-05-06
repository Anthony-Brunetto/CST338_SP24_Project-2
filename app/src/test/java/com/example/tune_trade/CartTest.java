package com.example.tune_trade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.example.tune_trade.database.entities.Cart;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CartTest {
    Cart cart;

    @Before
    public void setup(){
        cart = new Cart(3);
    }

    @After
    public void teardown(){
        cart = null;
    }

    @Test
    public void getId(){
        assertEquals(0, cart.getId());
    }

    @Test
    public void setId(){
        assertNotEquals(5, cart.getId());
        cart.setId(5);
        assertEquals(5, cart.getId());
    }
    @Test
    public void getUserId(){
        assertEquals(3, cart.getUserId());
    }
    @Test
    public void setUserId(){
        assertNotEquals(5,cart.getUserId());
        cart.setUserId(5);
        assertEquals(5,cart.getUserId());
    }

    @Test
    public void getProducts(){
        assertNull(cart.getProducts());
    }

    @Test
    public void setProducts(){
        assertNotEquals("4", cart.getProducts());
        cart.setProducts("4");
        assertEquals("4",cart.getProducts());
    }


}
