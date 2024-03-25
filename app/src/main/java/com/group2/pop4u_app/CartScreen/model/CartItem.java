package com.group2.pop4u_app.CartScreen.model;

public class CartItem {
    int thumb;
    String name;
    String option;
    String price;
    String quantity;


    public CartItem(int thumb, String name, String option, String price, String quantity) {
        this.thumb = thumb;
        this.name = name;
        this.option = option;
        this.price = price;
        this.quantity = quantity;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
