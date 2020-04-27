package com.example.restaurantmenu.Localdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.restaurantmenu.Localdb.dao.CartDao;
import com.example.restaurantmenu.Localdb.dao.CategoryDao;
import com.example.restaurantmenu.Localdb.dao.ItemDao;
import com.example.restaurantmenu.Localdb.dao.SubCategoryDao;
import com.example.restaurantmenu.Localdb.entities.Cart;
import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.Localdb.entities.SubCategory;
import com.example.restaurantmenu.Utill.Constants;

@Database(entities = {Category.class, Item.class,SubCategory.class, Cart.class}, version = 1)
public abstract class MenuDatabase extends RoomDatabase {

    public abstract CategoryDao getCategoryDao();

    public abstract ItemDao geItemDao();

    public abstract SubCategoryDao getSubCategoryDao();

    public abstract CartDao getCartDao();

    private static MenuDatabase instance;

    public static MenuDatabase getDatabase(Context context) {

        if (instance == null) {
            synchronized (MenuDatabase.class) {
                if (instance == null) {

                    instance = Room.databaseBuilder(context, MenuDatabase.class, Constants.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();


                }
            }
        }
        return instance;
    }

}
