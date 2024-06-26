package com.group2.api.Services;

import android.util.Log;
import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.ProductResponseDAO;
import com.group2.model.Product;

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

    public CompletableFuture<ArrayList<Product>> getListProduct(Integer page , String type_filter, String order, Integer limit, Integer rating, String artist_code, Integer price_start, Integer price_end, String category) {
        CompletableFuture<ArrayList<Product>> future = new CompletableFuture<>();
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductResponseDAO> call = service.getListProduct(page, type_filter, order, limit, rating, artist_code, price_start, price_end, category);

            try {
                Response<ProductResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ProductResponseDAO productResponse = response.body();
                    if (productResponse == null) {
                        future.completeExceptionally(new NullPointerException("Product not found")); // Complete the future exceptionally if product is null
                        return;
                    }
                    ArrayList<ProductDAO> listProductDAO = new ArrayList<>(productResponse.getProductList());
                    ArrayList<Product> products = new ArrayList<>();
                    for (ProductDAO productDAO : listProductDAO) {
                        products.add(productDAO.asProduct());
                    }
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

    public CompletableFuture<ArrayList<Product>> getProductByCategory(String category,
                                                                      Integer page,
                                                                      String order,
                                                                      Integer limit,
                                                                      Integer rating) {
        CompletableFuture<ArrayList<Product>> future = new CompletableFuture<>();
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductResponseDAO> call = service.getProductByCategory(category, page, order, limit, rating);

            try {
                Response<ProductResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ProductResponseDAO productResponse = response.body();
                    if (productResponse == null) {
                        future.completeExceptionally(new NullPointerException("Product not found")); // Complete the future exceptionally if product is null
                        return;
                    }
                    ArrayList<ProductDAO> listProductDAO = new ArrayList<>(productResponse.getProductList());
                    ArrayList<Product> products = new ArrayList<>();
                    for (ProductDAO productDAO : listProductDAO) {
                        products.add(productDAO.asProduct());
                    }
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
    public CompletableFuture<Product> getProduct(String product_code) {
        CompletableFuture<Product> future = new CompletableFuture<>();
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductDAO> call = service.getProductDetail(product_code);

            try {
                Response<ProductDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ProductDAO product = response.body();
                    if (product != null) {
                        future.complete(product.asProduct()); // Complete the future with the ProductDAO object
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