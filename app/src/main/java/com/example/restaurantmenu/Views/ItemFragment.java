package com.example.restaurantmenu.Views;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.Utill.Constants;
import com.example.restaurantmenu.ViewModel.ItemFragmentViewModel;
import com.example.restaurantmenu.adapter.ItemAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class ItemFragment extends Fragment {

    public ItemFragment() {
    }

    private static final String TAG = "ItemFragment";
    private CoordinatorLayout coordinatorLayout;
    private ItemFragmentViewModel fragmentViewModel;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;


    public static ItemFragment setCategoryId(int val) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.SUB_CAT_ID, val);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyleview);
        ItemAdapter itemAdapter;
        progressBar = view.findViewById(R.id.fragment_progress_bar);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
         fragmentViewModel = new ViewModelProvider(this).get(ItemFragmentViewModel.class);
        fragmentViewModel.getMessage().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Snackbar.make(coordinatorLayout,s,Snackbar.LENGTH_SHORT).show();

            }
        });
        if (getArguments() != null) {

            int subCatId = getArguments().getInt(Constants.SUB_CAT_ID, 0);
            List<Item> list = fragmentViewModel.getItemsById(subCatId);
            itemAdapter = new ItemAdapter(list);
            itemAdapter.setOnFloatingButtonClickListener(fragmentViewModel);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(itemAdapter);

        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentViewModel.getProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("Message", "onChanged: "+aBoolean);

                if (aBoolean){
                    //show progress bar
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
                    //dismiss progress dialog
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
