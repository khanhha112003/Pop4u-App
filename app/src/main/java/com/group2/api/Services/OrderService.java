package com.group2.api.Services;
import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.local.LoginManagerTemp;
import com.group2.model.ResponseValidate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class OrderService {
    public static final OrderService instance = new OrderService();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final IOrderService apiService = ServiceGenerator.createService(IOrderService.class);

    public CompletableFuture<ResponseValidate> getCart() {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = apiService.getCart(authHeader);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null) {
                        future.complete(user.asResponseValidate()); // Complete the future with the UserDAO object
                    } else {
                        future.complete(null); // Complete the future with null if the response is not successful
                    }
                } else {
                    future.complete(null); // Complete the future with null if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }
    public CompletableFuture<ResponseValidate> addProductToCart(String product_name, Integer quantity) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = apiService.addProductToCart(authHeader, product_name, quantity);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null) {
                        future.complete(user.asResponseValidate()); // Complete the future with the UserDAO object
                    } else {
                        future.complete(null); // Complete the future with null if the response is not successful
                    }
                } else {
                    future.complete(null); // Complete the future with null if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }
    public CompletableFuture<ResponseValidate> updateCart(String product_name, Integer quantity){
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = apiService.updateCart(authHeader, product_name, quantity);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null) {
                        future.complete(user.asResponseValidate()); // Complete the future with the UserDAO object
                    } else {
                        future.complete(null); // Complete the future with null if the response is not successful
                    }
                } else {
                    future.complete(null); // Complete the future with null if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }

    public CompletableFuture<ResponseValidate> createOrder(){
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = apiService.createOrder(authHeader);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null) {
                        future.complete(user.asResponseValidate()); // Complete the future with the UserDAO object
                    } else {
                        future.complete(null); // Complete the future with null if the response is not successful
                    }
                } else {
                    future.complete(null); // Complete the future with null if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }
    public CompletableFuture<ResponseValidate> deteleUpdateItem(String product_name, Integer quantity){
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = apiService.deteleUpdateItem(authHeader, product_name, quantity);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null) {
                        future.complete(user.asResponseValidate()); // Complete the future with the UserDAO object
                    } else {
                        future.complete(null); // Complete the future with null if the response is not successful
                    }
                } else {
                    future.complete(null); // Complete the future with null if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }

    public CompletableFuture<ResponseValidate> dropCart() {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = apiService.dropCart(authHeader);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null) {
                        future.complete(user.asResponseValidate()); // Complete the future with the UserDAO object
                    } else {
                        future.complete(null); // Complete the future with null if the response is not successful
                    }
                } else {
                    future.complete(null); // Complete the future with null if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }
}
