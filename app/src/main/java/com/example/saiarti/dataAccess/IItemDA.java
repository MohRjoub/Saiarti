package com.example.saiarti.dataAccess;

import java.util.List;

public interface IItemDA {
    String[] getCategories();
    List<Item> getItems();
    List<Item> getItems(String category);

}
