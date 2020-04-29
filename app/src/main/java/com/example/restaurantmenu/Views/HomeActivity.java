package com.example.restaurantmenu.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.Localdb.entities.SubCategory;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;
import com.example.restaurantmenu.Network.MySingletonClass;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.Utill.Constants;
import com.example.restaurantmenu.ViewModel.HomeViewModel;
import com.example.restaurantmenu.adapter.DynamicFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView textCartItemCount;
    private EditText searchEdit;
    private HomeViewModel homeViewModel;
    private ProgressBar progressBar;
    private MutableLiveData<List<SubCategory>> subcategoryData =new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Taste Of India");
        progressBar = findViewById(R.id.progress_bar);
        searchEdit = findViewById(R.id.edit_search);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        MenuRepository menuRepository =MenuRepository.getMenuRepository();
        menuRepository.initDao(getApplication());

        if (homeViewModel.getAllSubcategories().size() == 0){

            progressBar.setVisibility(View.VISIBLE);

            new FetchMenuAsyncTask(getApplication(),menuRepository , subcategoryData).execute();
            subcategoryData.observe(this, new Observer<List<SubCategory>>() {
                @Override
                public void onChanged(List<SubCategory> subCategoryList) {
                    showCategories(subCategoryList);
                    progressBar.setVisibility(View.GONE);
                }
            });

        }
        else {

            showCategories(homeViewModel.getAllSubcategories());
        }


    }

    private void showCategories(final List<SubCategory> subCategoryList) {
        Log.d(TAG, "showCategories: ");

        for (int k = 0; k < subCategoryList.size(); k++    ) {

            tabLayout.addTab(tabLayout.newTab().setText(subCategoryList.get(k).getSubCategoryName()));
        }


        DynamicFragmentAdapter adapter = new DynamicFragmentAdapter
                (getSupportFragmentManager(), subCategoryList, -1);
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }




    @Override
    protected void onResume() {
        super.onResume();
        homeViewModel.getNumOfCartItems().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int count = integer;
                if (textCartItemCount != null) {

                    textCartItemCount.setText(String.valueOf(count));

                }
            }
        });

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    
                    if (searchEdit.getText().toString().trim().isEmpty()){
                        Toast.makeText(HomeActivity.this, "Please enter dish name", Toast.LENGTH_SHORT).show();
                        
                    }
                    else {
                        Intent intent = new Intent(HomeActivity.this,SearchResultActivity.class);
                        intent.putExtra(Constants.SEARCHED_QUERY,searchEdit.getText().toString());
                        startActivity(intent);
                    }
                    
                }
                return false;
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        int count = homeViewModel.getNumOfCartItems().getValue();
        textCartItemCount.setText(String.valueOf(count));
        return true;
    }

    private static class FetchMenuAsyncTask extends AsyncTask<Void, Void, Void> {

        private Application application;
        private MenuRepository menuRepository;
        private MutableLiveData<List<SubCategory>> categoryMutableLiveData;


        FetchMenuAsyncTask(Application application, MenuRepository menuRepository,MutableLiveData<List<SubCategory>>categoryMutableLiveData) {
            this.application = application;
            this.menuRepository =menuRepository;
            menuRepository.initDao(application);
            this.categoryMutableLiveData = categoryMutableLiveData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constants.REQUEST_URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d(TAG, "onResponse: "+response);
                            try {
                                JSONObject obj = (JSONObject) response.get("Result_Output");

                                JSONArray catArray = (JSONArray) obj.get("categories");
                                JSONArray itemArray = (JSONArray) obj.get("items");
                                JSONArray subCatArray = (JSONArray) obj.get("subCategories");
                                List<SubCategory> list = new ArrayList<>();
                                for (int i = 0; i < catArray.length(); i++) {

                                    JSONObject jsonObject = (JSONObject) catArray.get(i);
                                    if (jsonObject.getString("categoryStatus").equals("Active")) {
                                        Category category = new Category(
                                                jsonObject.getString("categoryStatus"),
                                                jsonObject.getString("categoryName"),
                                                jsonObject.getInt("categoryID")

                                        );
                                        menuRepository.insertCategory(category);

                                    }




                                }


                                for (int i = 0; i < itemArray.length(); i++) {

                                    JSONObject jsonObject = (JSONObject) itemArray.get(i);
                                    if (jsonObject.getString("itemStatus").equals("Active")) {

                                        String itemType = "";
                                        int itemId = -1;
                                        String imageUrl = "";
                                        if (jsonObject.has("itemImageUrl")) {

                                            if (!jsonObject.isNull("itemImageUrl")) {

                                                imageUrl = jsonObject.getString("itemImageUrl");
                                            }
                                        }
                                        if (jsonObject.has("itemType")) {

                                            if (!jsonObject.isNull("itemType")) {

                                                itemType = jsonObject.getString("itemType");
                                            }
                                        }
                                        if (jsonObject.has("itemID")) {


                                            itemId = jsonObject.getInt("itemID");

                                        }
                                        Item item = new Item(
                                                itemType,
                                                jsonObject.getInt("subCategoryID"),
                                                itemId,
                                                jsonObject.getString("itemName"),
                                                jsonObject.getString("itemStatus"),
                                                jsonObject.getString("itemPrice"),
                                                jsonObject.getString("itemDeliveryType"),
                                                imageUrl


                                        );
                                        menuRepository.insertItem(item);


                                    }
                                }


                                for (int i = 0; i < subCatArray.length(); i++) {

                                    JSONObject jsonObject = (JSONObject) subCatArray.get(i);
                                    SubCategory subCategory = new SubCategory(

                                            jsonObject.getString("subCategoryStatus"),
                                            jsonObject.getString("subCategoryCode"),
                                            jsonObject.getInt("subCategoryID"),
                                            jsonObject.getString("categoryID"),
                                            jsonObject.getString("subCategoryName")


                                    );
                                    list.add(subCategory);
                                    menuRepository.insertSubCategory(subCategory);
                                }
                                categoryMutableLiveData.setValue(list);


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(TAG, "onResponse: " + e.getMessage());

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                public byte[] getBody() {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(Constants.CUSTOMERUSERPK, "1");
                    map.put(Constants.USER_NAME, "GUEST_USER");
                    map.put(Constants.SIGNETURE_KEY, "1234");
                    map.put(Constants.ACCOUNT_ID, "1");
                    map.put(Constants.SITE_ID, "1");
                    map.put(Constants.APP_END, "USER");
                    map.put(Constants.LAST_UPDATED_DATE, "1970-01-01 12:00:00");
                    return new JSONObject(map).toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };

            MySingletonClass.getInstance(application).addToRequestQueue(jsonObjectRequest);

        return null;
        }
    }

}


