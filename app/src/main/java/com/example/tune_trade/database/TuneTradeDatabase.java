package com.example.tune_trade.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.MainActivity;
import com.example.tune_trade.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class, User.class}, version = 2, exportSchema = false)
public abstract class TuneTradeDatabase extends RoomDatabase {

    public static final String productTable = "productTable";
    public static final String USER_TABLE = "userTable";

    private static volatile TuneTradeDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TuneTradeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TuneTradeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TuneTradeDatabase.class,
                            "tuneTradeDatabase")
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED");
            databaseWriteExecutor.execute(()->{
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1", "admin's house");
                admin.setAdmin(true);
                dao.insert(admin);
                User testuser1 = new User("testuser1", "testuser1", "testuser1's house");
                dao.insert(testuser1);
            });
        }
    };

    public abstract ProductDAO productDAO();

    public abstract UserDAO userDAO();
}
