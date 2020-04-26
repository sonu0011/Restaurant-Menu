package com.example.restaurantmenu.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.ViewModel.ItemViewModel;
import com.example.restaurantmenu.adapter.ItemAdapter;

import java.util.List;


public class BlankFragment extends Fragment {

    public BlankFragment() {
    }

    private View view;
    private int catId;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ItemViewModel itemViewModel;
    private static final String TAG = "BlankFragment";


    public static BlankFragment setCategoryId(int val) {
        BlankFragment fragment = new BlankFragment();
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

        if (getArguments() != null) {
            catId = getArguments().getInt("catId", 0);
            Log.d(TAG, "onCreateView: catId is "+catId);
            itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
            List<Item> list = itemViewModel.getItemsById(catId);
            Log.d(TAG, "onCreateView: listSize "+list.size());
            itemAdapter = new ItemAdapter(list);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(itemAdapter);

        }
        return view;
    }


}
