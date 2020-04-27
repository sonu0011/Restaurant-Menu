package com.example.restaurantmenu.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmenu.Localdb.entities.Cart;
import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.Utill.Constants;
import com.example.restaurantmenu.Utill.OnFloatingButtonClickListner;
import com.example.restaurantmenu.ViewModel.CartViewModel;
import com.example.restaurantmenu.ViewModel.CategoryViewModel;
import com.example.restaurantmenu.ViewModel.ItemViewModel;
import com.example.restaurantmenu.adapter.ItemAdapter;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity  implements OnFloatingButtonClickListner {

    private String searched_query;
    private ItemViewModel itemViewModel;
    private TextView heading;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private CartViewModel cartViewModel;
    private TextView textCartItemCount;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searched_query = getIntent().getStringExtra(Constants.SEARCHED_QUERY);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        heading = findViewById(R.id.query_string);
        heading.setText(searched_query);
        recyclerView = findViewById(R.id.searchRecycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        cartViewModel  = new ViewModelProvider(this).get(CartViewModel.class);
        coordinatorLayout =findViewById(R.id.searchcoordinatorLayout);
        List<Item> list = itemViewModel.getSearchedItems(searched_query);

        if (list.size() == 0) {
            Toast.makeText(this, "No Result Found", Toast.LENGTH_LONG).show();
        } else {

            itemAdapter = new ItemAdapter(list);
            itemAdapter.setOnFloatingButtonClickListner(this);
            recyclerView.setAdapter(itemAdapter);
        }


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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        int count = cartViewModel.getCartCount().getValue();
        textCartItemCount.setText(String.valueOf(count));

        return true;
    }

    @Override
    public void onClick(int itemId) {

        if (cartViewModel.isItemPresentInCart(itemId) > 0){
            Snackbar.make(coordinatorLayout,"This Item is already present in your cart",Snackbar.LENGTH_SHORT).show();

        }
        else {

            cartViewModel.insertIntoCart(new Cart(itemId));
            Snackbar.make(coordinatorLayout,"Item is added to your cart",Snackbar.LENGTH_SHORT).show();


        }
    }
}
