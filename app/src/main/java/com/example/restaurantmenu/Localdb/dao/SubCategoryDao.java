package com.example.restaurantmenu.Localdb.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restaurantmenu.Localdb.entities.SubCategory;

import java.util.List;

@Dao
public interface SubCategoryDao {

    @Insert
    void insertSubCategory(SubCategory subCategory);

    @Query("SELECT * FROM Subcategories")
    List<SubCategory> getSubcategories();
}
