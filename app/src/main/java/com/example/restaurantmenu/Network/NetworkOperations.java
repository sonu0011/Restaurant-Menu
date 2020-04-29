package com.example.restaurantmenu.Network;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.restaurantmenu.Localdb.entities.Category;
import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.Localdb.entities.SubCategory;
import com.example.restaurantmenu.Localdb.repositories.MenuRepository;
import com.example.restaurantmenu.Utill.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkOperations {
    private static final String TAG = "NetworkOperations";
    private Application application;
    private MenuRepository menuRepository;

    public NetworkOperations(Application application) {

        this.application = application;
        menuRepository = MenuRepository.getMenuRepository();
        menuRepository.initDao(application);
    }

    public void fetchDataFromNetwork(MutableLiveData<List<SubCategory>> subcategoryListLiveData) {
        final MutableLiveData<List<SubCategory>> listMutableLiveData =subcategoryListLiveData;

        new Thread(new Runnable() {
            @Override
            public void run() {

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
                                    listMutableLiveData.setValue(list);


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

            }
        }).start();

    }

}


