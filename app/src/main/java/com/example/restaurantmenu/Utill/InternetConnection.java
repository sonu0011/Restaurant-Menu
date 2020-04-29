package com.example.restaurantmenu.Utill;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnection {

    private Context context;

    public InternetConnection(Context context) {

        this.context = context;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.isActiveNetworkMetered();
    }
}
