package com.group2.api.Services;

import com.group2.api.DAO.ArtistDAO;
import com.group2.api.DAO.ArtistResponseDAO;
import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.api.DAO.VoucherDAO;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IVoucherService {
    @GET("/api/voucher/validate_voucher")
    Call<ValidationResponseDAO> validateVoucher(@Header("Authorization") String authHeader, @Query("voucher_code") String voucherCode);
    @GET("/api/voucher/get_all_voucher")
    Call<Collection<VoucherDAO>> getListVoucher(@Header("Authorization") String authHeader);
}
