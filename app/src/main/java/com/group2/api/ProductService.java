package com.group2.api;

import android.util.Log;
import retrofit2.Call;
import java.io.IOException;
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
                Log.d("Product url", response.raw().request().url().toString());
                if (response.isSuccessful()) {
                    // Deserialize JSON string to ProductResponse object
                    ProductResponseDAO productResponse = response.body();
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
}