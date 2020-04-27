package com.example.restaurantmenu.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.restaurantmenu.Localdb.dao.CartDao;
import com.example.restaurantmenu.Localdb.entities.Cart;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;

public class CartViewModel extends AndroidViewModel {
    private LiveData<Integer> cartCount;
    private MenuRepository menuRepository;

    public CartViewModel(@NonNull Application application) {
        super(application);
        menuRepository = new MenuRepository(application);
        cartCount = menuRepository.getCartCount();


    }
    public int isItemPresentInCart(int itemId){
        return menuRepository.isItemPresentInCart(itemId);
    }
    public void insertIntoCart(Cart cart){
        menuRepository.insertIntoCart(cart);
    }

    public LiveData<Integer> getCartCount() {
        return cartCount;
    }
}
