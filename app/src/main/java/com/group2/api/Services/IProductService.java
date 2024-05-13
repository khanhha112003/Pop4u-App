package com.group2.api.Services;
import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.ProductResponseDAO;
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
            @Query("artist_code") String artist_code,
            @Query("p_start") Integer price_start,
            @Query("p_end") Integer price_end
    );

    @GET("/api/product/product_list")
    Call<ProductResponseDAO> getProductByCategory(
            @Query("category") String category,
            @Query("page") Integer page,
            @Query("order") String order,
            @Query("limit") Integer limit,
            @Query("rating") Integer rating
    );

    @GET("/api/product/product_detail")
    Call<ProductDAO> getProductDetail(
            @Query("product_code") String product_code
    );

    @GET("/api/product/product_list")
    Call<ProductResponseDAO> getArtistProductList(
            @Query("limit") Integer limit,
            @Query("artist_code") String artist_code
    );
}
