package com.example.restaurantmenu.Localdb.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restaurantmenu.Localdb.entities.Cart;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertIntoCart(Cart itemId);

    @Query("SELECT COUNT(*) FROM cart")
    LiveData<Integer> getCartCount();

    @Query("SELECT count(itemID) FROM cart WHERE itemId =:itemId")
    int isItemPresentInCart(int itemId);
}
