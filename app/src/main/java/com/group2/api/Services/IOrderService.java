package com.group2.api.Services;
import com.group2.api.DAO.CartDAO;
import com.group2.api.DAO.OrderDetailDAO;
import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.model.Order;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IOrderService {
    @GET("/api/order/cart")
    Call<CartDAO> getCart(
            @Header("Authorization") String authHeader
    );

    @POST("/api/order/add_to_cart_mobile")
    Call<ValidationResponseDAO> updateCart(
            @Header("Authorization") String authHeader,
            @Query("product_code") String product_code,
            @Query("quantity") Integer quantity
    );

    @POST("/api/order/create_order_mobile")
    Call<ValidationResponseDAO> createOrder(
            @Header("Authorization") String authHeader,
            @Body HashMap<String, Object> body
    );

    @GET("/api/order/get_list_order")
    Call<ArrayList<OrderDetailDAO>> getOrder(
            @Header("Authorization") String authHeader,
            @Query("status") String status
    );

    @GET("/api/order/order_detail")
    Call<OrderDetailDAO> getOrderDetail(
            @Header("Authorization") String authHeader,
            @Query("order_code") String orderCode
    );

    @POST("/api/order/cancel_order")
    Call<ValidationResponseDAO> cancelOrder(
            @Header("Authorization") String authHeader,
            @Query("order_code") String orderCode
    );
}
