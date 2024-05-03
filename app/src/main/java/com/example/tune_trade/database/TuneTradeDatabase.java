package com.example.tune_trade.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tune_trade.StringListConverter;
import com.example.tune_trade.database.entities.Cart;
import com.example.tune_trade.database.entities.Product;
import com.example.tune_trade.MainActivity;
import com.example.tune_trade.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class, User.class, Cart.class}, version = 1, exportSchema = false)
@TypeConverters({StringListConverter.class})
public abstract class TuneTradeDatabase extends RoomDatabase {

    public static final String PRODUCT_TABLE = "productTable";
    public static final String USER_TABLE = "userTable";

    public static final String CART_TABLE = "cartTable";

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
                UserDAO userDao = INSTANCE.userDAO();
                CartDAO cartDao = INSTANCE.cartDAO();
                ProductDAO productDAO = INSTANCE.productDAO();
                cartDao.deleteAll();
                userDao.deleteAll();
                productDAO.deleteAll();
                User admin = new User("admin2", "admin2", "admin's house");
                admin.setAdmin(true);
                Cart adminCart = new Cart(userDao.insert(admin));
                cartDao.insert(adminCart);
                User testuser1 = new User("testuser1", "testuser1", "testuser1's house");
                Cart testuser1Cart = new Cart(userDao.insert(testuser1));
                cartDao.insert(testuser1Cart);
                Product globalDiscount = new Product("GLOBAL DISCOUNT",
                        -1,
                        -1.0,
                        "Keeps track of the global discount on products",
                        null);
                Product kingKruleAlbum = new Product("6 Feet Beneath The Moon - King Krule",
                        4,
                        25.50,
                        "6 Feet Beneath the Moon, by King Krule, is his first full-length on True Panther Sounds, and with it, the much anticipated unveiling of the full scope and scale of his vision.",
                        "Vinyls");
                Product gibson = new Product("Les Paul Standard 50s P-90, Tobacco Burst",
                        1,
                        2500.00,
                        "The Les Paul Standard 50s P-90 has a solid mahogany body with a maple top and a rounded 50s-style mahogany neck with a rosewood fingerboard and trapezoid inlays.",
                        "Instruments");
                Product walkman = new Product("128Gb Walkman, Grey",
                        2,
                        44.99,
                        "Y1 is not only a Walkman mp3 player, but also an audiobook player. It provides A-B repeat, bookmark and Speed options, which greatly facilitates your listening to audio book.",
                        "Music Players");
                productDAO.insert(globalDiscount);
                productDAO.insert(kingKruleAlbum);
                productDAO.insert(gibson);
                productDAO.insert(walkman);
            });
        }
    };

    public abstract ProductDAO productDAO();

    public abstract UserDAO userDAO();

    public abstract CartDAO cartDAO();
}
