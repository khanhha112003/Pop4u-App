package com.group2.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    String productCode;
    int productImage1;
    String productName;
    String productArtistName;
    String productLabel;
    int productPrice;
    int productComparingPrice;
    int productSalePercent;
    Double productRating;
    int productRatingCount;
    int productSoldAmount;
    int productStock;
    String productDescription;

    public Product(String productCode, String productName, int productImage1, String productArtistName, String productLabel, int productPrice, int productComparingPrice, int productSalePercent, Double productRating, int productRatingCount, int productSoldAmount, int productStock, String productDescription) {
        this.productCode = productCode;
        this.productName = productName;
        this.productImage1 = productImage1;
        this.productArtistName = productArtistName;
        this.productLabel = productLabel;
        this.productPrice = productPrice;
        this.productComparingPrice = productComparingPrice;
        this.productSalePercent = productSalePercent;
        this.productRating = productRating;
        this.productRatingCount = productRatingCount;
        this.productSoldAmount = productSoldAmount;
        this.productStock = productStock;
        this.productDescription = productDescription;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public int getProductPrice() {
        return productPrice;
    }
    public int getProductSalePercent() {
        return productSalePercent;
    }
    public Double getProductRating() {
        return productRating;
    }
    public int getProductSoldAmount() {
        return productSoldAmount;
    }
    public int getProductImage1() {
        return productImage1;
    }
    public String getProductArtistName() {
        return productArtistName;
    }
    public String getProductPriceInFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        return decimalFormat.format(this.productPrice);
    }

}
