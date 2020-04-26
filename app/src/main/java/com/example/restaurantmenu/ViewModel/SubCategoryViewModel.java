package com.example.restaurantmenu.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.Localdb.entities.SubCategory;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;

import java.util.List;

public class SubCategoryViewModel extends AndroidViewModel {
    private MenuRepository menuRepository;
    private List<SubCategory> subCategoryList;

    public SubCategoryViewModel(@NonNull Application application) {
        super(application);
        menuRepository = new MenuRepository(application);
        subCategoryList = menuRepository.getAllSubcategories();
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void insertSubCategory(SubCategory subCategory) {
        menuRepository.insertSubCategory(subCategory);
    }

}
