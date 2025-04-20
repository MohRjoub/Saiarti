package com.example.saiarti;

import java.util.List;

public class Order {
    private double sum;
    private List<CartItem> items;
    private String date;

    public Order(double sum, List<CartItem> items, String date) {
        this.sum = sum;
        this.items = items;
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
        }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return  "Order Date: " + date + ", Total fees: " + sum;
    }

}
