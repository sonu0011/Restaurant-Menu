package com.example.restaurantmenu.Localdb.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restaurantmenu.Localdb.entities.Category;
import java.util.List;


@Dao
public interface CategoryDao {

    @Insert()
    void insertCategory(Category category);

    @Query("SELECT * FROM Categories")
    List<Category> getCategories();

}
