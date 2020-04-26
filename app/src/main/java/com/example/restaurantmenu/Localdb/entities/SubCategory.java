package com.example.restaurantmenu.Localdb.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "Subcategories")
public class SubCategory {

    private String subCategoryStatus;
    private String subCategoryCode;
    @PrimaryKey
    @NonNull
    private int subCategoryID;

    @ForeignKey(entity = Category.class,
            parentColumns = "categoryID",
            childColumns = "categoryID")
    private String categoryID;
    private String subCategoryImageUrl;
    private String subCategoryName;

    public SubCategory(String subCategoryStatus, String subCategoryCode, int subCategoryID, String categoryID, String subCategoryImageUrl, String subCategoryName) {
        this.subCategoryStatus = subCategoryStatus;
        this.subCategoryCode = subCategoryCode;
        this.subCategoryID = subCategoryID;
        this.categoryID = categoryID;
        this.subCategoryImageUrl = subCategoryImageUrl;
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryStatus() {
        return subCategoryStatus;
    }

    public String getSubCategoryCode() {
        return subCategoryCode;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getSubCategoryImageUrl() {
        return subCategoryImageUrl;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

}
