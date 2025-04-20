package com.example.saiarti;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class Orders extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        listView = findViewById(R.id.listView);

        Intent intent = getIntent();
        Gson gson = new Gson();
        String str = intent.getStringExtra("orders");
        Order[] orders = gson.fromJson(str, Order[].class);
        ArrayAdapter<Order> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orders);
        listView.setAdapter(adapter);
    }
}
