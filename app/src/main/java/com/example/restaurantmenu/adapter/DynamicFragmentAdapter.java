package com.example.restaurantmenu.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Views.BlankFragment;

import java.util.List;

public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private List<Category> categoryList;

    public DynamicFragmentAdapter(FragmentManager fm, List<Category> categoryList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = categoryList.size();
        this.categoryList = categoryList;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        return BlankFragment.setCategoryId(categoryList.get(position).getCategoryID());
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
