package com.group2.api.Services;

import com.group2.api.DAO.ArtistDAO;
import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.SearchItemDAO;
import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.model.SearchItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class SearchService {
    public static final SearchService instance = new SearchService();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ISearchService searchService = ServiceGenerator.createService(ISearchService.class);

    public CompletableFuture<ArrayList<SearchItem>> search(String query_product) {
        CompletableFuture<ArrayList<SearchItem>> future = new CompletableFuture<>();
        executor.execute(() -> {
            Call<ArrayList<SearchItemDAO>> call = searchService.search(query_product);
            try {
                Response<ArrayList<SearchItemDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    ArrayList<SearchItemDAO> searchRequestData = response.body();
                    ArrayList<SearchItem> res = new ArrayList<>();
                    if (searchRequestData == null) {
                        future.completeExceptionally(new NullPointerException("Product not found")); // Complete the future exceptionally if product is null
                        return;
                    }
                    for (SearchItemDAO items : searchRequestData) {
                        res.add(items.asSearchItem());
                    }
                    future.complete(res); // Complete the future with the list of products
                } else {
                    future.completeExceptionally(new Exception("Product Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }

    public CompletableFuture<ArrayList<Product>> searchProduct(String query_product) {
        CompletableFuture<ArrayList<Product>> future = new CompletableFuture<>();
        executor.execute(() -> {
            Call<ArrayList<ProductDAO>> call = searchService.searchProduct(query_product);
            try {
                Response<ArrayList<ProductDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    ArrayList<ProductDAO> productsRequestData = response.body();
                    ArrayList<Product> products = new ArrayList<>();
                    if (productsRequestData == null) {
                        future.completeExceptionally(new NullPointerException("Product not found")); // Complete the future exceptionally if product is null
                        return;
                    }
                    for (ProductDAO productDAO : productsRequestData) {
                        products.add(productDAO.asProduct());
                    }
                    future.complete(products); // Complete the future with the list of products
                } else {
                    future.completeExceptionally(new Exception("Product Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }


    public CompletableFuture<ArrayList<Artist>> searchArtist(String query) {
        CompletableFuture<ArrayList<Artist>> future = new CompletableFuture<>();
        executor.execute(() -> {
            Call<ArrayList<ArtistDAO>> call = searchService.searchArtist(query);
            try {
                Response<ArrayList<ArtistDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    ArrayList<ArtistDAO> artistResponse = response.body();
                    if (artistResponse == null) {
                        future.completeExceptionally(new NullPointerException("Artist not found")); // Complete the future exceptionally if artist is null
                        return;
                    }
                    ArrayList<Artist> artistList = new ArrayList<>();
                    for (ArtistDAO artistDAO : artistResponse) {
                        artistList.add(artistDAO.asArtist());
                    }
                    future.complete(artistList); // Complete the future with the ArtistResponseDAO object
                } else {
                    future.completeExceptionally(new Exception("Artist Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }
}
