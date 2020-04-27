package com.example.restaurantmenu.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Views.ItemFragment;

import java.util.List;

public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private List<Category> categoryList;
    private int subcatId;

    public DynamicFragmentAdapter(FragmentManager fm, List<Category> categoryList,int subcatId) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = categoryList.size();
        this.categoryList = categoryList;
        this.subcatId = subcatId;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        if (subcatId == -1){
            //swipe fragment
            return ItemFragment.setCategoryId(categoryList.get(position).getCategoryID());

        }
        else {
            //tab selected
            return ItemFragment.setCategoryId(subcatId);

        }
    }

    @Override
    public int getCount() {

        if (subcatId == -1){
            //swipe fragment
            return  mNumOfTabs;
        }
        else {
            //tab selected
            return 1;

        }
    }
}
