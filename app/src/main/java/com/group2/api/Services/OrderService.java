package com.group2.api.Services;
import com.group2.api.DAO.CartDAO;
import com.group2.api.DAO.OrderDetailDAO;
import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.local.LoginManagerTemp;
import com.group2.model.CartItem;
import com.group2.model.OrderDetail;
import com.group2.model.ResponseValidate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class OrderService {
    public static final OrderService instance = new OrderService();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final IOrderService apiService = ServiceGenerator.createService(IOrderService.class);

    public CompletableFuture<ArrayList<CartItem>> getCart() {
        CompletableFuture<ArrayList<CartItem>> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<CartDAO> call = apiService.getCart(authHeader);
            try {
                Response<CartDAO> response = call.execute();
                if (response.isSuccessful()) {
                    CartDAO cartData = response.body();
                    if (cartData != null) {
                        ArrayList<CartItem> cartItems = cartData.asCartItems();
                        future.complete(cartItems); // Complete the future with the UserDAO object
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

    public CompletableFuture<ResponseValidate> deteleUpdateItem(String product_name, Integer quantity) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = apiService.updateCart(authHeader, product_name, -quantity);
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

    public CompletableFuture<ResponseValidate> createOrder(String address, String phone, String payment_method, String shipping_price, ArrayList<CartItem> listItem, String voucher) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        HashMap<String, Object> body = new HashMap<>();
        body.put("address", address);
        body.put("phone", phone);
        body.put("payment_method", payment_method);
        body.put("shipping_price", shipping_price);
        body.put("voucher", voucher);
        ArrayList<String> product_code = new ArrayList<>();
        ArrayList<String> quantity = new ArrayList<>();
        for (int i = 0; i < listItem.size(); i++) {
            product_code.add(listItem.get(i).getProductCode());
            quantity.add(String.valueOf(listItem.get(i).getQuantity()));
        }
        body.put("product_code", product_code);
        body.put("items_per_product", quantity);
        String authHeader = "Bearer " + LoginManagerTemp.token;
        executor.execute(() -> {
            Call<ValidationResponseDAO> call = apiService.createOrder(authHeader, body);
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

    public CompletableFuture<ArrayList<OrderDetail>> getListOrder(String status) {
        CompletableFuture<ArrayList<OrderDetail>> future = new CompletableFuture<>();
        String authHeader = "Bearer " + LoginManagerTemp.token;

        executor.execute(() -> {
            Call<ArrayList<OrderDetailDAO>> call = apiService.getOrder(authHeader, status);
            try {
                Response<ArrayList<OrderDetailDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    ArrayList<OrderDetailDAO> orderData = response.body();
                    if (orderData != null) {
                        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
                        for (OrderDetailDAO orderDetailDAO : orderData) {
                            orderDetails.add(orderDetailDAO.asOrderDetail());
                        }
                        future.complete(orderDetails); // Complete the future with the UserDAO object
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

    public CompletableFuture<OrderDetail> getOrderDetail(String orderCode) {
        CompletableFuture<OrderDetail> future = new CompletableFuture<>();
        String authHeader = "Bearer " + LoginManagerTemp.token;
        executor.execute(() -> {
            Call<OrderDetailDAO> call = apiService.getOrderDetail(authHeader, orderCode);
            try {
                Response<OrderDetailDAO> response = call.execute();
                if (response.isSuccessful()) {
                    OrderDetailDAO orderDetailDAO = response.body();
                    if (orderDetailDAO != null) {
                        future.complete(orderDetailDAO.asOrderDetail()); // Complete the future with the UserDAO object
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

    public CompletableFuture<ResponseValidate> cancelOrder(String orderCode) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        String authHeader = "Bearer " + LoginManagerTemp.token;
        executor.execute(() -> {
            Call<ValidationResponseDAO> call = apiService.cancelOrder(authHeader, orderCode);
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
