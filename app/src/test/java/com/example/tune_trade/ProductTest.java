package com.example.tune_trade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.tune_trade.database.entities.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {
    Product product;

    @Before
    public void setup(){
        product = new Product("Guitar", 5, 49.00, "Black", "Instruments" );
    }

    @After
    public void teardown(){
        product = null;
    }

    @Test
    public void getProductName(){
        assertEquals("Guitar", product.getName());
    }

    @Test
    public void setProductName(){
        assertNotEquals("Piano", product.getName());
        product.setName("Piano");
        assertEquals("Piano", product.getName());
    }
    @Test
    public void getProductCount(){
        assertEquals(5, product.getCount());
    }
    @Test
    public void setProductCount(){
        assertNotEquals(3,product.getCount());
        product.setCount(3);
        assertEquals(3,product.getCount());
    }

    @Test
    public void getProductPrice(){
        assertEquals(49.00, product.getPrice(), 0);
    }

    @Test
    public void setProductPrice(){
        assertNotEquals(30.00, product.getPrice());
        product.setPrice(10);
        assertEquals(10,product.getPrice(),0);
    }

    @Test
    public void getProductDescription(){
        assertEquals("Black", product.getDescription());
    }

    @Test
    public void setProductDescription(){
        assertNotEquals("Blue", product.getDescription());
        product.setDescription("Blue");
        assertEquals("Blue", product.getDescription());
    }

    @Test
    public void getProductCategory(){
        assertEquals("Instruments", product.getCategory());
    }

    @Test
    public void setProductCategory(){
        assertNotEquals("Vinyls", product.getCategory());
        product.setCategory("Vinyls");
        assertEquals("Vinyls", product.getCategory());
    }


}
