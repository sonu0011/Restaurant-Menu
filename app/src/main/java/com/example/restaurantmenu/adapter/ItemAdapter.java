package com.example.restaurantmenu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmenu.Localdb.entities.Item;
import com.example.restaurantmenu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<Item> list;

    public ItemAdapter(List<Item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item =  list.get(position);
        Picasso.get().load(item.getItemImageUrl()).into(holder.item_image);
        holder.item_price.setText("$ "+item.getItemPrice());
        holder.item_name.setText(item.getItemName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_name,item_price;
        FloatingActionButton floatingActionButton;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_title);
            item_price = itemView.findViewById(R.id.item_price);
            floatingActionButton = itemView.findViewById(R.id.item_add_cart);

        }
    }
}
