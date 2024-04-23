package com.group2.api.Services;
import com.group2.api.DAO.ArtistDAO;
import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.SearchItemDAO;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISearchService {
    @GET("/api/search")
    Call<ArrayList<SearchItemDAO>> search(
            @Query("keyword") String query
    );

    @GET("/api/search/artist")
    Call<ArrayList<ArtistDAO>> searchArtist(
            @Query("artist_name") String product_name
    );

    @GET("/api/product/product")
    Call<ArrayList<ProductDAO>> searchProduct(
            @Query("product_name") String product_name
    );
}
