package com.example.saiarti;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saiarti.dataAccess.IItemDA;
import com.example.saiarti.dataAccess.ItemDA;

public class MainActivity2 extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private Button cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prepareViews();
    }

    private void prepareViews() {
        searchView = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.recyclerView);
        cart = findViewById(R.id.cart);
        IItemDA itemDA = new ItemDA(this);
        ItemAdapter adapter = new ItemAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setItems(itemDA.getItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cart.setOnClickListener(v -> {
            Intent intent = new Intent(this, cart.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

    }


}