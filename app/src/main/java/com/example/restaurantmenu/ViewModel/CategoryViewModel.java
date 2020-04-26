package com.example.restaurantmenu.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private MenuRepository menuRepository;
    private List<Category> allcategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        menuRepository = new MenuRepository(application);
        allcategories = menuRepository.getAllCategories();
    }

    public List<Category> getAllcategories() {
        return allcategories;
    }

    public void insertCategory(Category category) {
        menuRepository.insertCategory(category);
    }

}
