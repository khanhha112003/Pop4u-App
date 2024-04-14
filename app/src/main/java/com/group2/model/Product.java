package com.group2.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Product {
    String productCode;
    String productName;
    String productArtistName;
    String productLabel;

    String artistCode;
    int productPrice;
    int productComparingPrice;
    int productSalePercent;
    Double productRating;
    int productRatingCount;
    int productSoldAmount;
    int productStock;
    String productDescription;

    ArrayList<String> listProductPhoto;

    public Product(String productCode,
                   String productName,
                   ArrayList<String> listProductPhoto,
                   String productArtistName,
                   String artistCode,
                   String productLabel,
                   int productPrice,
                   int productComparingPrice,
                   int productSalePercent,
                   Double productRating,
                   int productRatingCount,
                   int productSoldAmount,
                   int productStock,
                   String productDescription) {
        this.productCode = productCode;
        this.productName = productName;
        this.productArtistName = productArtistName;
        this.productLabel = productLabel;
        this.artistCode = artistCode;
        this.productPrice = productPrice;
        this.productComparingPrice = productComparingPrice;
        this.productSalePercent = productSalePercent;
        this.productRating = productRating;
        this.productRatingCount = productRatingCount;
        this.productSoldAmount = productSoldAmount;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.listProductPhoto = listProductPhoto;
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

    public String getArtistCode() {
        return artistCode;
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

    public String getBannerPhoto() {
        return listProductPhoto.get(0);
    }

    public String getProductDescription() {
        return productDescription;
    }

    public ArrayList<String> getListProductPhoto() {
        return listProductPhoto;
    }

    public String getProductArtistName() {
        return productArtistName;
    }

    public int getProductComparingPrice() {
        return productComparingPrice;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductComparingPrice(int productComparingPrice) {
        this.productComparingPrice = productComparingPrice;
    }

    public String getProductPriceInFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        return decimalFormat.format(this.productPrice);
    }
}
