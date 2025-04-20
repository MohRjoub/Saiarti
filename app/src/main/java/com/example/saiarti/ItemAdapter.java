package com.example.saiarti;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saiarti.dataAccess.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> items;
    private List<Item> searchItems;

    private MainActivity2 context;

    public ItemAdapter(MainActivity2 context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = searchItems.get(position);
        holder.itemName.setText(item.getName());
        holder.itemImage.setImageResource(item.getImage());
        holder.itemPrice.setText(item.getPrice());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("itemName", item.getName());
            intent.putExtra("itemDescription", item.getDescription());
            intent.putExtra("itemPrice", item.getPrice());
            intent.putExtra("itemRating", item.getRating());
            intent.putExtra("itemReviews", item.getReviews());
            intent.putExtra("itemImage", item.getImage());
            intent.putExtra("itemCategory", item.getCategory());
            intent.putExtra("itemId", item.getId());
            intent.putExtra("quantity", item.getQuantity());
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    public void setItems(List<Item> items) {
        List<Item> availables = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() > 0) {
                availables.add(item);
            }
        }
        this.items = availables;
        searchItems = new ArrayList<>(availables);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        searchItems.clear();
        if (text.isEmpty()) {
            searchItems.addAll(items);
        } else {
            text = text.toLowerCase();
            for (Item item : items) {
                if (item.getName().toLowerCase().contains(text) || item.getCategory().toLowerCase().contains(text)) {
                    searchItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage;
        TextView itemPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameTxv);
            itemImage = itemView.findViewById(R.id.itemImv);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
