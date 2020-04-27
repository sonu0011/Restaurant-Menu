package com.example.restaurantmenu.Localdb.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restaurantmenu.Localdb.entities.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insertItem(Item item);

    @Query("SELECT * FROM Items WHERE subCategoryID  = :itemId")
    List<Item> getItemsById(int itemId);

    @Query("SELECT * FROM Items WHERE itemName LIKE '%' || :dishName || '%'")
    List<Item> getSearchedResults(String dishName);



}
