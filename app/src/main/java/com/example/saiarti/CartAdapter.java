package com.example.saiarti;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {
    private final Context context;
    private List<CartItem> items;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public CartAdapter(Context context, List<CartItem> items) {
        super(context, R.layout.cart_item, items);
        this.context = context;
        this.items = items;
        setupSharedPrefs();
    }

    private void setupSharedPrefs() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.cart), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.cart_item, null);
        }

        CartItem item = items.get(position);

        TextView name = view.findViewById(R.id.itemName);
        TextView price = view.findViewById(R.id.itemPrice);
        ImageView image = view.findViewById(R.id.itemImage);
        TextView quantity = view.findViewById(R.id.itemQuantity);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        Button plusButton = view.findViewById(R.id.incButton);
        Button minusButton = view.findViewById(R.id.decButton);

        name.setText(item.getItem().getName());
        price.setText(item.getItem().getPrice());
        image.setImageResource(item.getItem().getImage());
        quantity.setText(String.valueOf(item.getQuantity()));

        deleteButton.setOnClickListener(v -> {
            items.remove(position);

            Gson gson = new Gson();
            String updatedJson = gson.toJson(items);
            editor.putString(DetailsActivity.DATA, updatedJson);
            editor.commit();

            notifyDataSetChanged();
        });

        minusButton.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                quantity.setText(String.valueOf(item.getQuantity()));
                Gson gson = new Gson();
                String updatedJson = gson.toJson(items);
                editor.putString(DetailsActivity.DATA, updatedJson);
                editor.commit();
            }
        });

        plusButton.setOnClickListener(v -> {
            Gson gson = new Gson();
            String str = sharedPreferences.getString(DetailsActivity.DATA, "");
            if (!str.equals("")) {
                CartItem[] items = gson.fromJson(str, CartItem[].class);
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getItem().getId().equalsIgnoreCase(item.getItem().getId()) && item.getQuantity() < item.getItem().getQuantity()) {
                        item.setQuantity(item.getQuantity() + 1);
                        quantity.setText(String.valueOf(item.getQuantity()));
                        String updatedJson = gson.toJson(this.items);
                        editor.putString(DetailsActivity.DATA, updatedJson);
                        editor.commit();
                        return;
                    } else if (items[i].getItem().getId().equalsIgnoreCase(item.getItem().getId()) && item.getQuantity() >= item.getItem().getQuantity()){
                        Toast.makeText(context, "Item out of stock", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        return view;
    }
}
