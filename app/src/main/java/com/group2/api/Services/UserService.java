package com.group2.api.Services;

import android.util.Log;

import retrofit2.Call;

import com.group2.api.DAO.LoginResponseDAO;
import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.api.DAO.UserDAO;
import com.group2.database_helper.LoginDatabaseHelper;
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

    public CompletableFuture<Boolean> validateToken() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = userService.validateToken(authHeader);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null && user.getStatus() == 1){
                        System.out.println("Token is valid");
                        future.complete(true); // Complete the future with true
                    } else {
                        System.out.println("Token is invalid");
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
            Call<ValidationResponseDAO> call = userService.register(body);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO resultRegister = response.body();
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
            Call<ValidationResponseDAO> call = userService.otp_verification(body);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO resultRegister = response.body();
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

    public CompletableFuture<ResponseValidate> updateProfile(String email, String otp, String fullname, String name, String phone) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            HashMap<String, String> body = new HashMap<>();
            body.put("username", email);
            body.put("otp", otp);
            body.put("new_fullname", fullname);
            body.put("new_birthdate", name);
            body.put("new_phone_number", phone);
            Call<ValidationResponseDAO> call = userService.update_profile(body);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful() && response.body() != null){
                    ValidationResponseDAO result = response.body();
                    future.complete(result.asResponseValidate());
                } else {
                    future.complete(null);
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }

    public CompletableFuture<Boolean> logout() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        executor.execute(() -> {
            String authHeader = "Bearer " + LoginManagerTemp.token;
            Call<ValidationResponseDAO> call = userService.logout(authHeader);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO user = response.body();
                    if (user != null && user.getStatus() == 1){
                        System.out.println("Logout success");
                        LoginManagerTemp.token = null;
                        LoginManagerTemp.isLogin = false;
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

    public CompletableFuture<ResponseValidate> forgotPassword(String email) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            Call<ValidationResponseDAO> call = userService.forgot_password(email);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO result = response.body();
                    if (result == null) {
                        future.complete(null); // Complete the future with false if the response body is null
                        return;
                    }
                    if (result.getStatus() == 1) {
                        Log.d("ForgotPassword", "Forgot password success");
                    } else {
                        Log.d("ForgotPassword", "Forgot password failed");
                    }
                    future.complete(result.asResponseValidate()); // Complete the future with the ResponseValidate object
                } else {
                    future.complete(null); // Complete the future with false in case of unsuccessful response
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }


    public CompletableFuture<ResponseValidate> updateNewPassword(String email, String otp, String newPassword) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        executor.execute(() -> {
            HashMap<String, String> body = new HashMap<>();
            body.put("email", email);
            body.put("otp", otp);
            body.put("new_password", newPassword);
            Call<ValidationResponseDAO> call = userService.update_new_password(body);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO result = response.body();
                    if (result == null) {
                        future.complete(null); // Complete the future with false if the response body is null
                        return;
                    }
                    if (result.getStatus() == 1) {
                        Log.d("UpdateNewPassword", "Update new password success");
                    } else {
                        Log.d("UpdateNewPassword", "Update new password failed");
                    }
                    future.complete(result.asResponseValidate()); // Complete the future with the ResponseValidate object
                } else {
                    future.complete(null); // Complete the future with false in case of unsuccessful response
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an exception occurs
            }
        });
        return future;
    }
}
