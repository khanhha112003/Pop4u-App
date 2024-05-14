package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.CartItem;
import com.group2.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailDAO {

    @JsonProperty("username")
    private String username;

    @JsonProperty("order_date")
    private String orderDate;

    @JsonProperty("order_code")
    private String orderCode;

    @JsonProperty("total_price")
    private long totalPrice;

    @JsonProperty("status")
    private String status;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("is_paid")
    private boolean isPaid;

    @JsonProperty("is_buy_now")
    private boolean isBuyNow;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("order_product_info")
    private List<ProductInOrderDetailDAO> orderProductInfo;

    @JsonProperty("shipping_price")
    private long shippingPrice;

    @JsonProperty("coupon_code")
    private String couponCode;

    public OrderDetail asOrderDetail() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        for (ProductInOrderDetailDAO productInOrderDetail : orderProductInfo) {
            cartItems.add(productInOrderDetail.asCartItem());
        }
        return new OrderDetail(
                orderCode,
                username,
                (int) totalPrice,
                orderDate,
                status,
                address,
                phone,
                isPaid,
                isBuyNow,
                paymentMethod,
                cartItems,
                (int) shippingPrice,
                couponCode,
                orderCode,
                ""
        );
    }

}

