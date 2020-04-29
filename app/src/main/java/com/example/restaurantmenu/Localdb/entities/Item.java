package com.example.restaurantmenu.Localdb.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Items")
public class Item {

    @ForeignKey(entity = SubCategory.class,
            parentColumns = "subCategoryID", childColumns = "subCategoryID")

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int itemUniqeId;
    private String itemType;
    private int subCategoryID;
    private int itemID;
    private String itemName;
    private String itemStatus;
    private String itemPrice;
    private String itemDeliveryType;
    private String itemImageUrl;


    public Item(String itemType,int subCategoryID, int itemID, String itemName, String itemStatus, String itemPrice, String itemDeliveryType, String itemImageUrl) {
        this.itemType = itemType;
        this.subCategoryID = subCategoryID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
        this.itemPrice = itemPrice;
        this.itemDeliveryType = itemDeliveryType;
        this.itemImageUrl = itemImageUrl;

    }

    public void setItemUniqeId(int itemUniqeId) {
        this.itemUniqeId = itemUniqeId;
    }

    public int getItemUniqeId() {
        return itemUniqeId;
    }

    public String getItemType() {
        return itemType;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemDeliveryType() {
        return itemDeliveryType;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

}
