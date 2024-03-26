package com.group2.model;

public class Product {
    int productID;


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

    public Product(int productID, String productName, int productImage1, String productArtistName, String productLabel, int productPrice, int productComparingPrice, int productSalePercent, Double productRating, int productRatingCount, int productSoldAmount, int productStock, String productDescription) {
        this.productID = productID;
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

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductComparingPrice() {
        return productComparingPrice;
    }

    public void setProductComparingPrice(int productComparingPrice) {
        this.productComparingPrice = productComparingPrice;
    }

    public int getProductSalePercent() {
        return productSalePercent;
    }

    public void setProductSalePercent(int productSalePercent) {
        this.productSalePercent = productSalePercent;
    }

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public int getProductRatingCount() {
        return productRatingCount;
    }

    public void setProductRatingCount(int productRatingCount) {
        this.productRatingCount = productRatingCount;
    }

    public int getProductSoldAmount() {
        return productSoldAmount;
    }

    public void setProductSoldAmount(int productSoldAmount) {
        this.productSoldAmount = productSoldAmount;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(int productImage1) {
        this.productImage1 = productImage1;
    }

    public String getProductArtistName() {
        return productArtistName;
    }

    public void setProductArtistName(String productArtistName) {
        this.productArtistName = productArtistName;
    }

}
