package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.CartItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInOrderDetailDAO {
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("quantity")
    private long quantity;

    @JsonProperty("discount_price")
    private long discountPrice;

    @JsonProperty("sell_price")
    private long sellPrice;

    @JsonProperty("image")
    private String image;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_final_price")
    private long productFinalPrice;

    public CartItem asCartItem() {
        return new CartItem(productCode, image, productName, (int) sellPrice, (int) discountPrice, (int) quantity, false);
    }

}
