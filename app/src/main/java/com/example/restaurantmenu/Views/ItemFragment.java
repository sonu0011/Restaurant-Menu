package com.example.restaurantmenu.Views;

import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantmenu.Localdb.entities.Cart;
import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.Utill.OnFloatingButtonClickListner;
import com.example.restaurantmenu.ViewModel.CartViewModel;
import com.example.restaurantmenu.ViewModel.ItemViewModel;
import com.example.restaurantmenu.adapter.ItemAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class ItemFragment extends Fragment implements OnFloatingButtonClickListner {

    public ItemFragment() {
    }

    private View view;
    private int catId;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ItemViewModel itemViewModel;
    private CartViewModel cartViewModel;
    private static final String TAG = "ItemFragment";
    private CoordinatorLayout coordinatorLayout;



    public static ItemFragment setCategoryId(int val) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt("catId", val);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = view.findViewById(R.id.recyleview);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);

        if (getArguments() != null) {
            catId = getArguments().getInt("catId", 0);
            List<Item> list = itemViewModel.getItemsById(catId);
            itemAdapter = new ItemAdapter(list);
            itemAdapter.setOnFloatingButtonClickListner(this);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(itemAdapter);

        }
        return view;
    }


    @Override
    public void onClick(int itemId) {

        if (cartViewModel.isItemPresentInCart(itemId) > 0){
            Snackbar.make(coordinatorLayout,"This Item is already present in your cart",Snackbar.LENGTH_SHORT).show();

        }
        else {
            cartViewModel.insertIntoCart(new Cart(itemId));
            Snackbar.make(coordinatorLayout,"Item is added to your cart",Snackbar.LENGTH_SHORT).show();


        }



    }
}
