package com.group2.model;

import java.util.ArrayList;

public class OrderDetail {
    private String id;
    private String username;
    private String orderDate;
    private int totalPrice;
    private String status;
    private String address;
    private String phone;
    private boolean isPaid;
    private boolean isBuyNow;
    private String paymentMethod;
    private ArrayList<CartItem> cartItems; // Define this class as per your product info structure
    private int shippingPrice;
    private String couponCode;
    private String orderCode;
    private String note;

    public OrderDetail(String id, String username, int totalPrice, String orderDate, String status, String address, String phone, boolean isPaid, boolean isBuyNow, String paymentMethod, ArrayList<CartItem> cartItems, int shippingPrice, String couponCode, String orderCode, String note) {
        this.id = id;
        this.username = username;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.isPaid = isPaid;
        this.isBuyNow = isBuyNow;
        this.paymentMethod = paymentMethod;
        this.cartItems = cartItems;
        this.shippingPrice = shippingPrice;
        this.couponCode = couponCode;
        this.orderCode = orderCode;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isBuyNow() {
        return isBuyNow;
    }

    public void setBuyNow(boolean buyNow) {
        isBuyNow = buyNow;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(int shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
