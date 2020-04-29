package com.example.restaurantmenu.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantmenu.Localdb.entities.Cart;
import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;
import com.example.restaurantmenu.Utill.OnFloatingButtonClickListner;

import java.util.List;

public class ItemFragmentViewModel extends AndroidViewModel implements OnFloatingButtonClickListner {
    private final LiveData<Integer> numOfCartItems;
    private MenuRepository menuRepository;
    private MutableLiveData<String> message = new MutableLiveData<>();
    private LiveData<Boolean> progressBar;

    public ItemFragmentViewModel(@NonNull Application application) {
        super(application);
        menuRepository = MenuRepository.getMenuRepository();
        menuRepository.initDao(application);
        numOfCartItems = menuRepository.getCartCount();
        progressBar =menuRepository.getShowProgressBar();

    }

    public LiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    /**
     * HANDLE ONCLICK ON CART BUTTON
     */
    @Override
    public void onClick(int itemId) {
        if (isItemPresentInCart(itemId) > 0) {

            message.setValue("This Item is already present in your cart");
        }
        else {

            insertIntoCart(new Cart(itemId));
            message.setValue("Item is added to your cart");
        }
    }


    /**
     * INSERT INTO CART
     */
    private void insertIntoCart(Cart cart) {
        menuRepository.insertIntoCart(cart);
    }


    /**
     * GET ITEM BY CATEGORY ID
     */
    public List<Item> getItemsById(int categoryId) {
        return menuRepository.getItemsById(categoryId);
    }


    /**
     *CHECK IF ITEM IS ALREADY PRESENT IN CART OR NOT
     */
    private int isItemPresentInCart(int itemId) {
        return menuRepository.isItemPresentInCart(itemId);
    }

    /**
     * FOR SHOWING MESSAGE OF CART ADDED AND ALREADY PRESENT IN CARD
     */
    public MutableLiveData<String> getMessage() {
        return message;
    }

    /**
     * GETTING SEARCHED DISHES NAME
     */
    public List<Item> getSearchedItems(String dishName) {
        return menuRepository.getSearchedItems(dishName);
    }

    /**
     * GET NUMBER OF ITEMS IN CART
     */
    public LiveData<Integer> getNumOfCartItems() {
        return numOfCartItems;
    }

}
