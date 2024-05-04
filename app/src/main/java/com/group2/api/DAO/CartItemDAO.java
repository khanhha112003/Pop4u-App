package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.CartItem;

public class CartItemDAO {
    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("sell_price")
    private Integer price;

    @JsonProperty("discount_price")
    private Integer comparingPrice;

    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("image")
    private String productImage;

    @JsonProperty("product_name")
    private String productNane;

    public CartItem asCartItems() {
        return new CartItem(productCode, productImage, productNane, price, comparingPrice, quantity, true);
    }
}