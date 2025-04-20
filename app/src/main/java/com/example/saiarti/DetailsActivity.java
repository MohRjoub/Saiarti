package com.example.saiarti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saiarti.dataAccess.Item;
import com.google.gson.Gson;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    public static final String DATA = "DATA";
    private TextView itemNameTxv;
    private TextView itemDescriptionTxv;
    private TextView itemPriceTxv;
    private RatingBar itemRatingTxv;
    private TextView itemReviewsTxv;
    private ImageView itemImageTxv;
    private Button addToCart;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        setupSharedPrefs();
        viewSetUp();

    }

    private void setupSharedPrefs() {
        sharedPreferences = getSharedPreferences(getString(R.string.cart), MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void viewSetUp() {

        itemNameTxv = findViewById(R.id.txtName);
        itemDescriptionTxv = findViewById(R.id.txtDescription);
        itemPriceTxv = findViewById(R.id.txtPrice);
        itemRatingTxv = findViewById(R.id.ratingBar);
        itemReviewsTxv = findViewById(R.id.txtReviews);
        itemImageTxv = findViewById(R.id.itemImage);
        addToCart = findViewById(R.id.addToCart);

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String itemDescription = intent.getStringExtra("itemDescription");
        String itemPrice = intent.getStringExtra("itemPrice");
        String itemRating = intent.getStringExtra("itemRating");
        String itemReviews = intent.getStringExtra("itemReviews");
        int itemImage = intent.getIntExtra("itemImage", 0);
        String itemCategory = intent.getStringExtra("itemCategory");
        String itemId = intent.getStringExtra("itemId");
        int quantity = intent.getIntExtra("quantity", 0);

        itemNameTxv.setText(itemName);
        itemDescriptionTxv.setText(itemDescription);
        itemPriceTxv.setText(itemPrice);
        itemRatingTxv.setRating(Float.parseFloat(itemRating));
        itemReviewsTxv.setText(itemReviews);
        itemImageTxv.setImageResource(itemImage);

        addToCart.setOnClickListener(v -> {
            Gson gson = new Gson();
            String str = sharedPreferences.getString(DATA, "");
            if(!str.equals("") && quantity > 0) {
                CartItem[] items = gson.fromJson(str, CartItem[].class);
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getItem().getId().equalsIgnoreCase(itemId)) {
                        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
                        Log.d("Item added to cart already exist", "Item added to cart");
                        return;
                    }
                }
                    CartItem[] newItems = new CartItem[items.length + 1];
                    for (int j = 0; j < items.length; j++) {
                        newItems[j] = items[j];
                    }
                    newItems[items.length] = new CartItem();
                    newItems[items.length].setItem(new Item(itemName, itemId, itemDescription, itemPrice, itemRating, itemReviews, itemCategory, itemImage, quantity));
                    newItems[items.length].setQuantity(1);
                    str = gson.toJson(newItems);
                    editor.putString(DATA, str);
                    editor.commit();
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
                    Log.d("Item added to cart for first time", "Item added to cart");
                }
            else if(quantity > 0) {
                Item item = new Item(itemName, itemId, itemDescription, itemPrice, itemRating, itemReviews, itemCategory, itemImage, quantity);
                    CartItem[] items = new CartItem[1];
                    items[0] = new CartItem();
                    items[0].setItem(item);
                    items[0].setQuantity(1);
                    str = gson.toJson(items);
                    editor.putString(DATA, str);
                    editor.commit();
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
                    Log.d("Item added to cart for first time and cart is empty", "Item added to cart");
            } else {
                Toast.makeText(this, "Item out of stock", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

