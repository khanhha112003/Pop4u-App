package com.group2.pop4u_app.CartScreen.model;

public class CartItem {
    int thumb;
    String name;
    String option;
    int price;
    int quantity;
    private boolean isChecked;


    public CartItem(int thumb, String name, String option,  int price,  int quantity) {
        this.thumb = thumb;
        this.name = name;
        this.option = option;
        this.price = price;
        this.quantity = quantity;
        this.isChecked = false;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public int getPrice() {
        return price;
    }

    public void setPrice( int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity) {
        this.quantity = quantity;
    }

}
