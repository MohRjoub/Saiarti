package com.example.saiarti.dataAccess;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.saiarti.MainActivity;
import com.example.saiarti.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ItemDA implements IItemDA {

    private Context context;
    public static String ITEMS = "ITEMS";
    public static List<Item> items = new ArrayList<>();
    private String[] categories = new String[10];

    private SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public ItemDA(Context context) {
        this.context = context;
        setupSharedPrefs(context);
        saveTestItems();
        saveItems(items);
        loadItems();

    }

    @Override
    public String[] getCategories() {
        return categories;
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public List<Item> getItems(String category) {
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getCategory().equals(category)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    private void setupSharedPrefs(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }
    public void loadItems() {
        String str = sharedPreferences.getString(ITEMS, "");
        if (!str.equals("")) {
            Gson gson = new Gson();
            Item[] array = gson.fromJson(str, Item[].class);
            items = new ArrayList<>(Arrays.asList(array));
        } else {
            items = new ArrayList<>();
        }
    }

    public static void saveItems(List<Item> items) {
        Gson gson = new Gson();
        String json = gson.toJson(items);
        Log.d("JSON", json);
        editor.putString(ITEMS, json);
        editor.commit();
    }

    private void saveTestItems() {
        Item item1 = new Item("Car Phone holder", "1", "on glass phone holder suitable for all kind of mobile phones", "10 $", "4.5", "100 reviews", "Phone Accessories", R.drawable.item0, 10);
        Item item2 = new Item("Car Phone charger", "2", "charger suitable for all kind of mobile phones", "5 $", "4.5", "100 reviews", "Phone Accessories", R.drawable.item1, 10);
        Item item3 = new Item("Car perfume", "3", "very good smell perfume", "20 $", "4.5", "100 reviews", "Perfumes", R.drawable.item2, 10);
        Item item4 = new Item("Car camera", "4", "very good camera for recording", "15 $", "4.5", "100 reviews", "Cameras", R.drawable.item3, 10);
        Item item5 = new Item("Car intiror led lamp", "5", "very good lamp for interior", "10 $", "4.5", "100 reviews", "Lamps", R.drawable.item4, 10);
        Item item6 = new Item("Car front LED lamp", "6", "h4 plug for front LED lamp very good", "10 $", "4.5", "100 reviews", "Lamps", R.drawable.item5, 10);
        Item item7 = new Item("Car wheel LED lamp", "7", "LED lamp very good for car wheels", "3 $", "4.5", "100 reviews", "Lamps", R.drawable.item6, 10);
        Item item8 = new Item("Car seat cover", "8", "very good seat cover for all kind of cars", "90 $", "4.5", "100 reviews", "seat Accessories", R.drawable.item7, 10);
        Item item9 = new Item("Car steering wheel cover", "9", "very good steering wheel cover for all kind of cars", "3 $", "4.5", "100 reviews", "Steering Wheels", R.drawable.item8, 10);
        Item item10 = new Item("Car seat neck fan", "10", "very good seat neck fan for all kind of cars", "12 $", "4.5", "100 reviews", "fans", R.drawable.item9, 10);

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        items.add(item7);
        items.add(item8);
        items.add(item9);
        items.add(item10);
    }

}
