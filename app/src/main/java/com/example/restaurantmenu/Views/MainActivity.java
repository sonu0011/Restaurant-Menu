package com.example.restaurantmenu.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
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
import com.example.restaurantmenu.Network.MySingletonClass;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.Utill.Constants;
import com.example.restaurantmenu.ViewModel.CartViewModel;
import com.example.restaurantmenu.ViewModel.CategoryViewModel;
import com.example.restaurantmenu.ViewModel.ItemViewModel;
import com.example.restaurantmenu.ViewModel.SubCategoryViewModel;
import com.example.restaurantmenu.adapter.DynamicFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Home";
    private CategoryViewModel categoryViewModel;
    private ItemViewModel itemViewModel;
    private SubCategoryViewModel subCategoryViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CartViewModel cartViewModel;
    private ProgressBar progressBar;
    TextView textCartItemCount;
    private EditText searchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Taste Of India (Delivery)");
        searchEdit = findViewById(R.id.edit_search);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        progressBar = findViewById(R.id.progress_bar);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        subCategoryViewModel = new ViewModelProvider(this).get(SubCategoryViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);


        List<Category> categoryList = categoryViewModel.getAllcategories();
        if (categoryList.isEmpty()) {
            Log.d(TAG, "onCreate: categoryList IS empty");
            getMenuFromNetwork();
        } else {
            //GET DATA FROM LOCAL DATABASE
            Log.d(TAG, "onCreate: fetchFromLocalDb");
            setMenuAndItems(categoryList);


        }

    }

    private void setMenuAndItems(final List<Category> categoryList) {
        Log.d(TAG, "setMenuAndItems: ");

        for (int k = 0; k < categoryList.size(); k++) {

            tabLayout.addTab(tabLayout.newTab().setText(categoryList.get(k).getCategoryName()));
        }

        DynamicFragmentAdapter adapter = new DynamicFragmentAdapter
                (getSupportFragmentManager(), categoryList, -1);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

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


    private void getMenuFromNetwork() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "getMenuFromNetwork: ");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.REQUEST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response);

                        try {
                            JSONObject obj = (JSONObject) response.get("Result_Output");

                            JSONArray catArray = (JSONArray) obj.get("categories");
                            JSONArray itemArray = (JSONArray) obj.get("items");
                            JSONArray subCatArray = (JSONArray) obj.get("subCategories");
                            for (int i = 0; i < catArray.length(); i++) {

                                JSONObject jsonObject = (JSONObject) catArray.get(i);
                                if (jsonObject.getString("categoryStatus").equals("Active")) {
                                    Category category = new Category(
                                            jsonObject.getString("categoryStatus"),
                                            jsonObject.getString("categoryName"),
                                            jsonObject.getInt("categoryID")

                                    );


                                    //categoryList.add(category);
                                    categoryViewModel.insertCategory(category);
                                }

                            }


                            for (int i = 0; i < itemArray.length(); i++) {

                                JSONObject jsonObject = (JSONObject) itemArray.get(i);
                                if (jsonObject.getString("itemStatus").equals("Active")
                                        && !jsonObject.isNull("itemImageUrl")) {


                                    Item item = new Item(
                                            jsonObject.getInt("subCategoryID"),
                                            jsonObject.getInt("itemID"),
                                            jsonObject.getString("itemName"),
                                            jsonObject.getString("itemStatus"),
                                            jsonObject.getString("itemPrice"),
                                            jsonObject.getString("itemDeliveryType"),
                                            jsonObject.getString("itemImageUrl")


                                    );
                                    itemViewModel.insertItem(item);


                                }
                            }


                            for (int i = 0; i < subCatArray.length(); i++) {

                                JSONObject jsonObject = (JSONObject) subCatArray.get(i);
                                if (!jsonObject.isNull("subCategoryImageUrl")) {
                                    SubCategory subCategory = new SubCategory(

                                            jsonObject.getString("subCategoryStatus"),
                                            jsonObject.getString("subCategoryCode"),
                                            jsonObject.getInt("subCategoryID"),
                                            jsonObject.getString("categoryID"),
                                            jsonObject.getString("subCategoryImageUrl"),
                                            jsonObject.getString("subCategoryName")


                                    );

                                    subCategoryViewModel.insertSubCategory(subCategory);


                                }
                            }
                            progressBar.setVisibility(View.GONE);


//                            List<Category> categoryList = categoryViewModel.getAllcategories();
//                            List<Item> itemList = itemViewModel.getItemsById();
                            //setMenuAndItems(categoryList,itemList);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        progressBar.setVisibility(View.GONE);
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

        MySingletonClass.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }


    @Override
    protected void onResume() {
        super.onResume();
        cartViewModel.getCartCount().observe(this, new Observer<Integer>() {
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
                        Toast.makeText(MainActivity.this, "Please enter dish name", Toast.LENGTH_SHORT).show();
                        
                    }
                    else {
                        searchDishes();
                    }
                    
                }
                return false;
            }
        });


    }

    private void searchDishes() {
        Intent intent = new Intent(this,SearchResultActivity.class);
        intent.putExtra(Constants.SEARCHED_QUERY,searchEdit.getText().toString());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        int count = cartViewModel.getCartCount().getValue();
        textCartItemCount.setText(String.valueOf(count));

        return true;
    }


}


