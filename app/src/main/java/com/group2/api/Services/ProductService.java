package com.group2.api.Services;

import android.util.Log;
import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.ProductResponseDAO;

import retrofit2.Call;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Response;

public class ProductService {

    public static final ProductService instance = new ProductService();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public CompletableFuture<ProductResponseDAO> getListProduct(Integer page , String type_filter, String order, Integer limit, Integer rating, String artist_code) {
        CompletableFuture<ProductResponseDAO> future = new CompletableFuture<>();
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductResponseDAO> call = service.getListProduct(page, type_filter, order, limit, rating, artist_code);

            try {
                Response<ProductResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ProductResponseDAO productResponse = response.body();
                    future.complete(productResponse); // Complete the future with the ProductResponseDAO object
                } else {
                    future.completeExceptionally(new Exception("Product Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }

    public CompletableFuture<ProductDAO> getProduct(String product_code) {
        CompletableFuture<ProductDAO> future = new CompletableFuture<>();
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductDAO> call = service.getProductDetail(product_code);

            try {
                Response<ProductDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ProductDAO product = response.body();
                    if (product != null) {
                        future.complete(product); // Complete the future with the ProductDAO object
                    } else {
                        future.completeExceptionally(new NullPointerException("Product not found")); // Complete the future exceptionally if product is null
                    }
                } else {
                    future.completeExceptionally(new Exception("Product Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }

    public CompletableFuture<ArrayList<ProductDAO>> searchProduct(String query_product) {
        CompletableFuture<ArrayList<ProductDAO>> future = new CompletableFuture<>();
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ArrayList<ProductDAO>> call = service.searchProduct(query_product);

            try {
                Response<ArrayList<ProductDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    ArrayList<ProductDAO> products = response.body();
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

    public CompletableFuture<ArrayList<ProductDAO>> getArtistProductList(Integer limit, String artist_code) {
        CompletableFuture<ArrayList<ProductDAO>> future = new CompletableFuture<>();
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductResponseDAO> call = service.getArtistProductList(1000, artist_code);

            try {
                Response<ProductResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ProductResponseDAO productResponse = response.body();
                    if (productResponse == null) {
                        future.completeExceptionally(new NullPointerException("Product not found")); // Complete the future exceptionally if product is null
                        return;
                    }
                    ArrayList<ProductDAO> products = new ArrayList<>(productResponse.getProductList());
                    future.complete(products); // Complete the future with the ProductResponseDAO object
                } else {
                    future.completeExceptionally(new Exception("Product Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }
}