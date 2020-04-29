package com.example.restaurantmenu.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.Utill.Constants;
import com.example.restaurantmenu.ViewModel.ItemFragmentViewModel;
import com.example.restaurantmenu.adapter.ItemAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private TextView textCartItemCount;
    private CoordinatorLayout coordinatorLayout;
    private ItemFragmentViewModel itemFragmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Taste Of India");
        String searched_query = getIntent().getStringExtra(Constants.SEARCHED_QUERY);
        RecyclerView recyclerView = findViewById(R.id.searchRecycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        TextView textView = findViewById(R.id.heading);
        textView.setText("Showing results for " + searched_query + "");

        coordinatorLayout = findViewById(R.id.searchcoordinatorLayout);
        itemFragmentViewModel = new ViewModelProvider(this).get(ItemFragmentViewModel.class);

        List<Item> list = itemFragmentViewModel.getSearchedItems(searched_query);

        if (list.size() == 0) {

            findViewById(R.id.no_result_found).setVisibility(View.VISIBLE);
            findViewById(R.id.no_result_found_msg).setVisibility(View.VISIBLE);
        } else {

            ItemAdapter itemAdapter = new ItemAdapter(list);
            itemAdapter.setOnFloatingButtonClickListener(itemFragmentViewModel);

            recyclerView.setAdapter(itemAdapter);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        itemFragmentViewModel.getNumOfCartItems().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int count = integer;
                if (textCartItemCount != null) {

                    textCartItemCount.setText(String.valueOf(count));

                }
            }
        });

        itemFragmentViewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(coordinatorLayout, s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        int count = itemFragmentViewModel.getNumOfCartItems().getValue();
        textCartItemCount.setText(String.valueOf(count));

        return true;
    }


}
