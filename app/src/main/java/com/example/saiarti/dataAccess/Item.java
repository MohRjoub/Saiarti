package com.example.saiarti.dataAccess;

public class Item {
    private String name;
    private String id;
    private String description;
    private String price;
    private String rating;
    private String reviews;
    private String category;
    private int image;
    private int quantity;



    public Item() {}

    public Item(String name, String id, String description, String price, String rating, String reviews, String category, int image, int quantity) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.reviews = reviews;
        this.category = category;
        this.image = image;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", description='" + description + '\'' + ", price='" + price + '\'' + ", rating='" + rating + '\'' + ", reviews='" + reviews + '\'' + ", category='" + category + '\'' + ", image=" + image + ", quantity=" + quantity + '}';
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
