package com.example.restaurantmenu.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Localdb.entities.SubCategory;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private LiveData<Integer> numOfCartItems;
    private List<SubCategory> allSubcategories;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        MenuRepository menuRepository = MenuRepository.getMenuRepository();
        menuRepository.initDao(application);
        menuRepository.initDao(application);
        numOfCartItems = menuRepository.getCartCount();
        allSubcategories = menuRepository.getAllSubCategoryList();
        menuRepository.initDao(application);

    }

    /**
     * GET NUMBER OF ITEMS IN CART
     */
    public LiveData<Integer> getNumOfCartItems() {
        return numOfCartItems;
    }

    /**
     * GET ALL CATEGORIES
     */
    public List<SubCategory> getAllSubcategories() {
        return allSubcategories;
    }


}
