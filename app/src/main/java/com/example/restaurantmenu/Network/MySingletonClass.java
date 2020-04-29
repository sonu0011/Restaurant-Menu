package com.example.restaurantmenu.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletonClass {

    private static MySingletonClass mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    private MySingletonClass(Context context) {
        mContext = context;

        mRequestQueue = getRequestQueue();
    }

    public static synchronized MySingletonClass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingletonClass(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
