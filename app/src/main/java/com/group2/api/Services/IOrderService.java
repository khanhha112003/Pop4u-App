package com.group2.api.Services;
import com.group2.api.DAO.ValidationResponseDAO;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IOrderService {
    @GET("/api/order/cart")
    Call<ValidationResponseDAO> getCart(
            @Header("Authorization") String authHeader
    );

    @POST("/api/order/add_to_cart")
    Call<ValidationResponseDAO> addProductToCart(
            @Header("Authorization") String authHeader,
            @Query("product_code") String product_code,
            @Query("quantity") Integer quantity
    );

    @POST("/api/order/update_cart")
    Call<ValidationResponseDAO> updateCart(
            @Header("Authorization") String authHeader,
            @Query("product_name") String product_name,
            @Query("quantity") Integer quantity
    );

    @POST("/api/order/checkout")
    Call<ValidationResponseDAO> createOrder(
            @Header("Authorization") String authHeader
    );

    @DELETE("/api/order/delete_item_in_cart")
    Call<ValidationResponseDAO> deteleUpdateItem(
            @Header("Authorization") String authHeader,
            @Query("product_name") String product_name,
            @Query("quantity") Integer quantity
    );

    @DELETE("/api/order/delete_all_item_in_cart")
    Call<ValidationResponseDAO> dropCart(
            @Header("Authorization") String authHeader
    );
}
