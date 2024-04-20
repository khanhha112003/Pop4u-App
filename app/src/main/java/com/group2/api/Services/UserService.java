package com.group2.api.Services;

import android.util.Log;

import retrofit2.Call;

import com.group2.api.DAO.LoginResponseDAO;
import com.group2.api.DAO.RegisterFormResponseDAO;
import com.group2.api.DAO.UserDAO;
import com.group2.local.LoginManagerTemp;
import com.group2.model.ResponseValidate;
import com.group2.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class UserService {
    public static final UserService instance = new UserService();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final IUserService userService = ServiceGenerator.createService(IUserService.class);

    public CompletableFuture<String> login(String email, String password) {
        CompletableFuture<String> future = new CompletableFuture<>();
        executor.execute(() -> {
            Call<LoginResponseDAO> call = userService.login(email, password);
            try {
                Response<LoginResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    LoginResponseDAO user = response.body();
                    if (!user.getToken().isEmpty()) {
                        System.out.println("Login success");
                        future.complete(user.getToken()); // Complete the future with true
                    } else {
                        System.out.println("Login failed");
                        future.complete(null); // Complete the future with false
                    }
                } else {
                    future.complete(null); // Complete the future with false in case of unsuccessful response
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }

    public CompletableFuture<ResponseValidate> register(String email, String password) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            HashMap<String, String> body = new HashMap<>();
            body.put("email", email);
            body.put("password", password);
            Call<RegisterFormResponseDAO> call = userService.register(body);
            try {
                Response<RegisterFormResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    RegisterFormResponseDAO resultRegister = response.body();
                    if (resultRegister == null) {
                        future.complete(null); // Complete the future with false if the response body is null
                        return;
                    }
                    if (resultRegister.getStatus() == 1) {
                        Log.d("Register", "Register success");
                    } else {
                        Log.d("Register", "Register failed");
                    }
                    future.complete(resultRegister.asResponseValidate()); // Complete the future with the ResponseValidate object
                } else {
                    future.complete(null); // Complete the future with false in case of unsuccessful response
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }

    public CompletableFuture<ResponseValidate> validate_otp(String email, String otp) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            HashMap<String, String> body = new HashMap<>();
            body.put("email", email);
            body.put("otp", otp);
            Call<RegisterFormResponseDAO> call = userService.otp_verification(body);
            try {
                Response<RegisterFormResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    RegisterFormResponseDAO resultRegister = response.body();
                    if (resultRegister == null) {
                        future.complete(null); // Complete the future with false if the response body is null
                        return;
                    }
                    if (resultRegister.getStatus() == 1) {
                        Log.d("Validate", "Validate success");
                    } else {
                        Log.d("Validate", "Validate failed");
                    }
                    future.complete(resultRegister.asResponseValidate()); // Complete the future with the ResponseValidate object
                } else {
                    future.complete(null); // Complete the future with false in case of unsuccessful response
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }


//    CompletableFuture<Boolean> logoutFuture = logout();
//
//    // Attach a callback to the CompletableFuture to handle the result
//        logoutFuture.thenAccept(result -> {
//        if (result) {
//            // Logout was successful
//            System.out.println("Logout successful");
//        } else {
//            // Logout failed
//            System.out.println("Logout failed");
//        }
//    });

    public CompletableFuture<Boolean> logout() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<UserDAO> call = userService.logout(authHeader);
            try {
                Response<UserDAO> response = call.execute();
                if (response.isSuccessful()) {
                    UserDAO user = response.body();
                    if (user != null) {
                        System.out.println("Logout success");
                        future.complete(true); // Complete the future with true
                    } else {
                        System.out.println("Logout failed");
                        future.complete(false); // Complete the future with false
                    }
                } else {
                    future.complete(false); // Complete the future with false in case of unsuccessful response
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }

    public CompletableFuture<UserDAO> update(String email, String password, String name, String phone, String address) {
        CompletableFuture<UserDAO> future = new CompletableFuture<>();
        executor.execute(() -> {
            Call<UserDAO> call = userService.update(email, password, name, phone, address);
            try {
                Response<UserDAO> response = call.execute();
                if (response.isSuccessful()) {
                    future.complete(response.body()); // Complete the future with the UserDAO object
                } else {
                    future.complete(null); // Complete the future with null if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }

    public CompletableFuture<User> getUserProfile() {
        CompletableFuture<User> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<UserDAO> call = userService.getUserProfile(authHeader);
            try {
                Response<UserDAO> response = call.execute();
                if (response.isSuccessful()) {
                    UserDAO user = response.body();
                    if (user != null) {
                        future.complete(user.asUser()); // Complete the future with the UserDAO object
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
