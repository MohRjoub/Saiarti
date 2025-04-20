package com.example.saiarti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saiarti.dataAccess.ItemDA;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class cart extends AppCompatActivity {
    private List<CartItem> items;
    private ListView cart;
    private Button purchase, myOrders;
    private TextView emptyCartMessage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        setupSharedPrefs();
        viewSetUp();
        purchase.setOnClickListener(v -> {
            if (items != null && !items.isEmpty()) {
                double sum = 0;
                for (int i = 0; i < items.size(); i++) {
                    CartItem item = items.get(i);
                    if (item.getQuantity() > item.getItem().getQuantity()) {
                        Toast.makeText(this, "Item out of stock Select less quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    item.getItem().setQuantity(item.getItem().getQuantity() - item.getQuantity());
                    sum += Double.parseDouble(item.getItem().getPrice()) * item.getQuantity();
                    for (int j = 0; j < ItemDA.items.size(); j++) {
                        if (ItemDA.items.get(j).getId().equalsIgnoreCase(item.getItem().getId())) {
                            ItemDA.items.get(j).setQuantity(ItemDA.items.get(j).getQuantity() - item.getQuantity());
                        }
                    }
                }
                Gson gson = new Gson();
                String str = sharedPreferences.getString("orders", "");
                editor.clear();
                editor.commit();
                if (!str.equals("")) {
                    Order[] orders = gson.fromJson(str, Order[].class);
                    Order[] newOrders = new Order[orders.length + 1];
                    for (int j = 0; j < orders.length; j++) {
                        newOrders[j] = orders[j];
                    }
                    newOrders[orders.length] = new Order(sum, items, String.valueOf(new Date()));
                    str = gson.toJson(newOrders);
                    editor.putString("orders", str);
                    editor.commit();
                } else {
                    Order[] newOrders = new Order[1];
                    newOrders[0] = new Order(sum, items, String.valueOf(new Date()));
                    str = gson.toJson(newOrders);
                    editor.putString("orders", str);
                    editor.commit();
                }
                items.clear();
                ItemDA.saveItems(ItemDA.items);
                viewSetUp();
                Toast.makeText(this, "Purchase successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
            }
        });

        myOrders.setOnClickListener(v -> {
            Gson gson = new Gson();
            String str = sharedPreferences.getString("orders", "");
            if (!str.equals("")) {
                Intent intent = new Intent(this, Orders.class);
                intent.putExtra("orders", str);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewSetUp() {
        cart = findViewById(R.id.cartLtV);
        emptyCartMessage = findViewById(R.id.emptyCartMessage);
        purchase = findViewById(R.id.purchase);
        myOrders = findViewById(R.id.myOrders);
        loadCart();
        if (items == null || items.isEmpty()) {
            emptyCartMessage.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);
            purchase.setVisibility(View.GONE);
        } else {
            emptyCartMessage.setVisibility(View.GONE);
            cart.setVisibility(View.VISIBLE);
            purchase.setVisibility(View.VISIBLE);
            CartAdapter adapter = new CartAdapter(this, items);
            cart.setAdapter(adapter);
            cart.setOnItemClickListener((parent, view, position, id) -> {

            });
        }

    }

    private void setupSharedPrefs() {
        sharedPreferences = getSharedPreferences(getString(R.string.cart), MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void loadCart() {
        String str = sharedPreferences.getString(DetailsActivity.DATA, "");
        if (!str.equals("")) {
            Gson gson = new Gson();
            CartItem[] array = gson.fromJson(str, CartItem[].class);
            items = new ArrayList<>(Arrays.asList(array));
        } else {
            items = new ArrayList<>();
        }
    }


}
