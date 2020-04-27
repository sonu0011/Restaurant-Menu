package com.example.restaurantmenu.Localdb.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class Cart {

    @PrimaryKey
    @NonNull
    int itemId;

    public int getItemId() {
        return itemId;
    }

    public Cart(int itemId) {
        this.itemId = itemId;
    }
}
