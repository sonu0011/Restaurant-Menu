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
import com.example.restaurantmenu.Utill.OnFloatingButtonClickListner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> list;
    private OnFloatingButtonClickListner onFloatingButtonClickListner;


    public ItemAdapter(List<Item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = list.get(position);
        if (item.getItemImageUrl().equals("")){
            holder.item_image.setImageResource(R.drawable.placeholder);
        }
        else {
            Picasso picasso = Picasso.get();
            picasso.setIndicatorsEnabled(true);
            picasso.setLoggingEnabled(true);
            picasso.load(item.getItemImageUrl())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.item_image);
        }

        if (item.getItemType().equals("")){
            holder.image_vegan.setVisibility(View.GONE);
            holder.image_gluten.setVisibility(View.GONE);
        }
        else if (item.getItemType().contains(",")){
            holder.image_vegan.setImageResource(R.mipmap.vegan);
            holder.image_gluten.setImageResource(R.mipmap.gluten);
        }
        else {
            if (item.getItemType().equalsIgnoreCase("GF")){
                holder.image_vegan.setVisibility(View.GONE);
                holder.image_gluten.setImageResource(R.mipmap.gluten);
            }
            if (item.getItemType().equalsIgnoreCase("V")){
                holder.image_gluten.setVisibility(View.GONE);
                //holder.image_vegan.set
                holder.image_vegan.setImageResource(R.mipmap.vegan);

            }

        }
        holder.item_price.setText("$ " + Float.valueOf(item.getItemPrice()));
        holder.item_name.setText(item.getItemName());

    }

    public void setOnFloatingButtonClickListener(OnFloatingButtonClickListner onFloatingButtonClickListner) {
        this.onFloatingButtonClickListner = onFloatingButtonClickListner;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image, image_gluten, image_vegan;
        TextView item_name, item_price;
        FloatingActionButton floatingActionButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_gluten = itemView.findViewById(R.id.gluten);
            image_vegan = itemView.findViewById(R.id.vegan);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_title);
            item_price = itemView.findViewById(R.id.item_price);
            floatingActionButton = itemView.findViewById(R.id.item_add_cart);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFloatingButtonClickListner.onClick(list.get(getAdapterPosition()).getItemUniqeId());
                }
            });

        }
    }
}
