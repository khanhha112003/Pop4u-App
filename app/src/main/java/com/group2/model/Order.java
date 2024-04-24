package com.group2.model;

public class Order {
    String o_id;
    int o_thumb;
    String o_name;
    String o_artist;
    int o_price;
    int o_quantity;


    public Order(String o_id, int o_thumb, String o_name, String o_artist, int o_price, int o_quantity) {
        this.o_id = o_id;
        this.o_thumb = o_thumb;
        this.o_name = o_name;
        this.o_artist = o_artist;
        this.o_price = o_price;
        this.o_quantity = o_quantity;
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public int getO_thumb() {
        return o_thumb;
    }

    public void setO_thumb(int o_thumb) {
        this.o_thumb = o_thumb;
    }

    public String getO_name() {
        return o_name;
    }

    public void setO_name(String o_name) {
        this.o_name = o_name;
    }

    public String getO_artist() {
        return o_artist;
    }

    public void setO_artist(String o_artist) {
        this.o_artist = o_artist;
    }

    public int getO_price() {
        return o_price;
    }

    public void setO_price(int o_price) {
        this.o_price = o_price;
    }

    public int getO_quantity() {
        return o_quantity;
    }

    public void setO_quantity(int o_quantity) {
        this.o_quantity = o_quantity;
    }
}
