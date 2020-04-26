package com.example.restaurantmenu.Localdb.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.restaurantmenu.Localdb.dao.CategoryDao;
import com.example.restaurantmenu.Localdb.dao.ItemDao;
import com.example.restaurantmenu.Localdb.dao.SubCategoryDao;
import com.example.restaurantmenu.Localdb.database.MenuDatabase;
import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.Localdb.entities.SubCategory;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuRepository {

    private List<Category> categoryList;
    private List<Item> itemList;
    private List<SubCategory> subCategoryList;

    private CategoryDao categoryDao;
    private ItemDao itemDao;
    private SubCategoryDao subCategoryDao;

    public MenuRepository(Application application) {

        MenuDatabase menuDatabase = MenuDatabase.getDatabase(application);
        categoryDao = menuDatabase.getCategoryDao();
        itemDao = menuDatabase.geItemDao();
        subCategoryDao = menuDatabase.getSubCategoryDao();


    }

    public List<Category> getAllCategories() {

        try {
            categoryList = new GetCategoryAsync(categoryDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    public List<Item> getItemsById(int itemId) {
        try {
            itemList  = new GetItemsAsync(itemDao ,itemId).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public List<SubCategory> getAllSubcategories() {
        try {
            subCategoryList  =  new GetSubcategoriesAsync(subCategoryDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return subCategoryList;
    }

    public void insertCategory(Category category) {
        new InsertCategoryAsyncTask(categoryDao).execute(category);
    }

    public void insertItem(Item item) {

        new InsertItemAsyncTask(itemDao).execute(item);
    }

    public void insertSubCategory(SubCategory subCategory){

        new InsertSubcategoryAsyncTask(subCategoryDao).execute(subCategory);

    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insertCategory(categories[0]);
            return null;
        }
    }

    private static class GetCategoryAsync extends AsyncTask<Void, Void, List<Category>> {

        private CategoryDao categoryDao;

        GetCategoryAsync(CategoryDao dao) {
            categoryDao = dao;
        }


        @Override
        protected List<Category> doInBackground(Void... voids) {
            return categoryDao.getCategories();
        }
    }

    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao itemDao;

        InsertItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insertItem(items[0]);
            return null;
        }
    }

    private static class GetItemsAsync extends AsyncTask<Void, Void, List<Item>> {

        private ItemDao itemDao;
        int itemId;

        GetItemsAsync(ItemDao itemDao, int itemId) {
            this.itemDao = itemDao;
            this.itemId = itemId;
        }



        @Override
        protected List<Item> doInBackground(Void... voids) {
            return itemDao.getItemsById(itemId);
        }
    }

    private static class InsertSubcategoryAsyncTask extends AsyncTask<SubCategory, Void, Void> {

        private SubCategoryDao subCategoryDao;

        InsertSubcategoryAsyncTask(SubCategoryDao subCategoryDao) {
            this.subCategoryDao = subCategoryDao;
        }

        @Override
        protected Void doInBackground(SubCategory... subCategories) {
            subCategoryDao.insertSubCategory(subCategories[0]);
            return null;
        }
    }

    private static class GetSubcategoriesAsync extends AsyncTask<Void, Void, List<SubCategory>> {

        private SubCategoryDao subCategoryDao;

        GetSubcategoriesAsync(SubCategoryDao subCategoryDao) {
            this.subCategoryDao = subCategoryDao;
        }


        @Override
        protected List<SubCategory> doInBackground(Void... voids) {
            return subCategoryDao.getSubcategories();
        }
    }


}
