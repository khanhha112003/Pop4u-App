package com.group2.api;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
@JsonIgnoreProperties({ "artist_code", "reviews", "rating_detail", "is_freeship" ,"is_hot", "is_new", "is_sale", "is_trend"})
public class ProductDAO {
    @JsonProperty("product_code")
    private String productId;

    @JsonProperty("list_product_image")
    private Collection<String> listProductImage;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("artist")
    private String productArtistName;

    @JsonProperty("category")
    private String productLabel;

    @JsonProperty("sell_price")
    private int productPrice;

    @JsonProperty("discount_price")
    private int productComparingPrice;


    @JsonProperty("rating")
    private Double productRating;

    @JsonProperty("num_of_rating")
    private int productRatingCount;


    @JsonProperty("product_stock")
    private int productStock;

    @JsonProperty("description")
    private String productDescription;

    // Getters and setters

    public String getProductDescription() {
        return productDescription;
    }

}
