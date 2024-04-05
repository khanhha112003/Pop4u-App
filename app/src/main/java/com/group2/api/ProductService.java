package com.group2.api;

import android.util.Log;
import retrofit2.Call;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Response;

public class ProductService {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void getListProduct() {
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductResponseDAO> call = service.getListProduct(null, null, null, null, null, null);

            try {
                Response<ProductResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ProductResponseDAO productResponse = response.body();
                    assert productResponse != null;
                    for (ProductDAO product : productResponse.getProductList()) {
                        Log.d("Product Item", product.getProductDescription());
                    }
                } else {
                    Log.d("Product Network Request Error", "Error: " + response.code());
                }
            } catch (IOException e) {
                Log.e("Product Translate Error", "Exception: " + e);
            }
        });
    }

    public static void getProduct(String product_code) {
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ProductDAO> call = service.getProductDetail(product_code);

            try {
                Response<ProductDAO> response = call.execute();
                if (response.isSuccessful()) {
                    // Deserialize JSON string to Product object
                    ProductDAO product = response.body();
                    if (product == null) {
                        Log.d("Product Detail", "Product not found");
                        throw new NullPointerException();
                    }
                    Log.d("Product Item", product.getProductDescription());
                } else {
                    Log.d("Product Network Request Error", "Error: " + response.code());
                }
            } catch (IOException e) {
                Log.e("Product Translate Error", "Exception: " + e);
            }
        });
    }

    public static void searchProduct(String query_product) {
        executor.execute(() -> {
            IProductService service = ServiceGenerator.createService(IProductService.class);
            Call<ArrayList<ProductDAO>> call = service.searchProduct(query_product);

            try {
                Response<ArrayList<ProductDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    // Deserialize JSON string to Product object
                    ArrayList<ProductDAO> products = response.body();
                    if (products == null) {
                        Log.d("Product Detail", "Product not found");
                        throw new NullPointerException();
                    }
                    for (ProductDAO product : products) {
                        Log.d("Product Item", product.getProductDescription());
                    }
                } else {
                    Log.d("Product Network Request Error", "Error: " + response.code());
                }
            } catch (IOException e) {
                Log.e("Product Translate Error", "Exception: " + e);
            }
        });
    }
}