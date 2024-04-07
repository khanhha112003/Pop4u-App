package com.group2.api.Services;

import com.group2.api.DAO.UserDAO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUserService {
    @POST("/api/user/login")
    Call<UserDAO> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("/api/user/register")
    Call<UserDAO> register(
            @Query("email") String email,
            @Query("password") String password,
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("address") String address
    );

    @POST("/api/user/logout")
    Call<UserDAO> logout();

    @POST("/api/user/update")
    Call<UserDAO> update(
            @Query("email") String email,
            @Query("password") String password,
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("address") String address
    );

    @GET ("/api/user/user_profile")
    Call<UserDAO> getUserProfile();
}
