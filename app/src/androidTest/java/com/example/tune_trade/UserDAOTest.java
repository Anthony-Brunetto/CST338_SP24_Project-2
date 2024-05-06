package com.example.tune_trade;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.tune_trade.database.TuneTradeDatabase;
import com.example.tune_trade.database.UserDAO;
import com.example.tune_trade.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserDAOTest {
    private UserDAO userDAO;
    private TuneTradeDatabase db;

    @Before
    public void createDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TuneTradeDatabase.class).build();
        userDAO = db.userDAO();
    }

    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void insertTest(){
        //  Creates new user for us to pass in
        User testUser = new User("testUser", "testPassword", "testUser");
        //  Insert user into database
        userDAO.insert(testUser);
        //  Get list of users (the one we passed in) from database
        List<User> allUsers = userDAO.getAllUsersList();
        //  Assert that list size is equal to 1
        assertEquals(1, allUsers.size());
        //  Assert that usernames are equal
        assertEquals(testUser.getUsername(), allUsers.get(0).getUsername());
    }


    @Test
    public void updateTest(){
        //  Creates new user for us to pass in
        User testUser = new User("testUser", "testPassword", "testUser");
        //  Inserts user into database
        userDAO.insert(testUser);
        //  Retrieves list of users (the one we passed in) from the database
        List<User> allUsers = userDAO.getAllUsersList();
        //  Assert that list size is equal to 1
        assertEquals(1, allUsers.size());
        //  Assert that usernames are equal
        assertEquals(testUser.getUsername(), allUsers.get(0).getUsername());
        //  Set local user's username to "Mr Pojo"
        allUsers.get(0).setUsername("Mr Anthony");
        //  Send in user object with modified username to database
        userDAO.update(allUsers.get(0));
        //  Retrieve users from database (our user with updated username)
        allUsers = userDAO.getAllUsersList();
        //  Assert that user's new username is Mr Pojo
        assertEquals("Mr Anthony", allUsers.get(0).getUsername());
    }

    @Test
    public void deleteTest(){
        User testUser = new User("testUser", "testPassword", "testUser");
        userDAO.insert(testUser);
        //  Retrieves list of users (the one we passed in) from the database
        List<User> allUsers = userDAO.getAllUsersList();
        //  Assert that list size is equal to 1
        assertEquals(1, allUsers.size());
        //  Assert that usernames are equal
        assertEquals(testUser.getUsername(), allUsers.get(0).getUsername());
        //  Delete user that we sent into the database
        //  **DELETE USER BY SENDING IN TESTUSER DOES NOT WORK BECAUSE THE IDS ARE DIFFERENT
        userDAO.delete(allUsers.get(0));
        //  Retrieve all users (should be none inside since we deleted the only one)
        allUsers = userDAO.getAllUsersList();
        //  Assert that all users list size is 0
        assertEquals(0, allUsers.size());
    }


}

