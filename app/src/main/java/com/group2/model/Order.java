package com.group2.model;

import java.util.ArrayList;

public class Order {
    String o_id;
    String o_thumb;
    String o_name;
    String o_artist;
    int o_price;
    int o_quantity;
    int o_total_quantity;
    int o_total_price;

    ArrayList<CartItem> cartItems;

    public Order(String o_id, String o_thumb, String o_name, String o_artist, int o_price, int o_quantity) {
        this.o_id = o_id;
        this.o_thumb = o_thumb;
        this.o_name = o_name;
        this.o_artist = o_artist;
        this.o_price = o_price;
        this.o_quantity = o_quantity;
    }

    public String getO_id() { return o_id; }
    public String getO_thumb() {
        return o_thumb;
    }
    public String getO_name() {
        return o_name;
    }
    public String getO_artist() {
        return o_artist;
    }
    public int getO_price() {
        return o_price;
    }
    public int getO_quantity() {
        return o_quantity;
    }
}
