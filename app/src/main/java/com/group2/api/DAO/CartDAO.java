package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.CartItem;
import com.group2.model.User;

import java.util.ArrayList;

public class CartDAO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("date")
    private String date;

    @JsonProperty("total_price")
    private String total_price;

    @JsonProperty("products")
    private ArrayList<CartItemDAO> products;

    public ArrayList<CartItem> asCartItems() {
        ArrayList<CartItem> result = new ArrayList<CartItem>();
        for (CartItemDAO product : products) {
            result.add(product.asCartItems());
        }
        return result;
    }
}

