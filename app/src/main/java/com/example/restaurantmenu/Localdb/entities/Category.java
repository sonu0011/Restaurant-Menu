package com.example.restaurantmenu.Localdb.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories")
public class Category {
    private String categoryStatus;
    private String categoryName;

    @PrimaryKey
    @NonNull
    private int categoryID;


    public Category(String categoryStatus, String categoryName, int categoryID) {
        this.categoryStatus = categoryStatus;
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }
}
