package com.group2.api;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IProductService {
    @GET("/api/product/product_list")
    Call<ProductResponseDAO> getListProduct(
            @Query("page") Integer page,
            @Query("type_filter") String type_filter,
            @Query("order") String order,
            @Query("limit") Integer limit,
            @Query("rating") Integer rating,
            @Query("artist_code") String artist_code
    );

    @GET("/product/product_detail")
    Call<ProductDAO> getProductDetail(
            @Query("product_code") String product_code
    );

    @GET("/product/search_product")
    Call<List<ProductDAO>> searchProduct(
            @Query("product_name") String product_name
    );
}
