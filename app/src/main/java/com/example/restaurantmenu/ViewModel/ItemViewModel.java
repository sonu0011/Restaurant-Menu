package com.example.restaurantmenu.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private MenuRepository menuRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        menuRepository = new MenuRepository(application);

    }

    public List<Item> getItemsById(int id) {
        return  menuRepository.getItemsById(id);
    }

    public void insertItem(Item item) {
        menuRepository.insertItem(item);
    }
}
