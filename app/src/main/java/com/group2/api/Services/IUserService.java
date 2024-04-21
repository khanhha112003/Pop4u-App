package com.group2.api.Services;

import com.group2.api.DAO.LoginResponseDAO;
import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.api.DAO.UserDAO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUserService {
    @FormUrlEncoded
    @POST("/api/auth/login")
    Call<LoginResponseDAO> login(
            @Field("username") String email,
            @Field("password") String password
    );

    @POST("/api/auth/logout")
    Call<ValidationResponseDAO> logout(@Header("Authorization") String authHeader);

    @POST("/api/auth/mobile_register")
    Call<ValidationResponseDAO> register(@Body HashMap<String, String> body);

    @POST("/api/auth/mobile_verification")
    Call<ValidationResponseDAO> otp_verification(@Body HashMap<String, String> body);

    @POST("/api/auth/mobile_update_profile")
    Call<ValidationResponseDAO> update_profile(@Body HashMap<String, String> body);

    @POST("/api/user/update")
    Call<UserDAO> update(
            @Query("email") String email,
            @Query("password") String password,
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("address") String address
    );

    @GET ("/api/auth/user_profile")
    Call<UserDAO> getUserProfile(@Header("Authorization") String authHeader);
}
