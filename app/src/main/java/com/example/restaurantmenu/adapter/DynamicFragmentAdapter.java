package com.example.restaurantmenu.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.restaurantmenu.Localdb.entities.SubCategory;
import com.example.restaurantmenu.Views.ItemFragment;

import java.util.List;

public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private List<SubCategory> subCategoryList;
    private int subcatId;

    public DynamicFragmentAdapter(FragmentManager fm, List<SubCategory> categoryList, int subcatId) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = categoryList.size();
        this.subCategoryList = categoryList;
        this.subcatId = subcatId;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        if (subcatId == -1){
            //swipe fragment
            return ItemFragment.setCategoryId(subCategoryList.get(position).getSubCategoryID());

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
