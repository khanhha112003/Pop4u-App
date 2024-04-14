package com.group2.api.DAO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.Product;
import com.group2.pop4u_app.R;

import java.util.ArrayList;
import java.util.Collection;
@JsonIgnoreProperties({ "reviews", "rating_detail"})
public class ProductDAO {
    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("artist_code")
    private String artistCode;

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

    @JsonProperty("is_freeship")
    private boolean is_freeship;

    @JsonProperty("is_hot")
    private boolean is_hot;

    @JsonProperty("is_new")
    private boolean is_new;

    @JsonProperty("is_sale")
    private boolean is_sale;

    // Getters and setters

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public Product asProduct() {
        String label = "";
        if (is_hot) {
            label = "Bán chạy";
        } else if (is_new) {
            label = "Mới";
        } else if (is_sale) {
            label = "Giảm giá";
        }
        Integer salePercent = 0;
        if (productComparingPrice != 0) {
            salePercent = Math.round((productComparingPrice - productPrice) * 100 / productComparingPrice);
        }
        ArrayList<String> listProductImage = new ArrayList<>(this.listProductImage);
        return new Product(productCode, productName, listProductImage, productArtistName, artistCode, label, productPrice, productComparingPrice, salePercent, productRating, productRatingCount, 30, productStock, productDescription);
    }

}
