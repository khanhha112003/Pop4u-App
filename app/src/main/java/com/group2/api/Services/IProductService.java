package com.group2.api.Services;
import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.ProductResponseDAO;

import java.util.ArrayList;

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

    @GET("/api/product/product_detail")
    Call<ProductDAO> getProductDetail(
            @Query("product_code") String product_code
    );

    @GET("/api/product/search_product")
    Call<ArrayList<ProductDAO>> searchProduct(
            @Query("product_name") String product_name
    );

    @GET("/api/product/product_list")
    Call<ProductResponseDAO> getArtistProductList(
            @Query("limit") Integer limit,
            @Query("artist_code") String artist_code
    );
}
