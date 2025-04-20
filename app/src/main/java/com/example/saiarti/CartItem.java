package com.example.saiarti;

import androidx.annotation.NonNull;

import com.example.saiarti.dataAccess.Item;

public class CartItem {
    private Item item;
    private int quantity;

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " " + quantity;
    }
}
