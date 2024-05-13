package com.group2.api.Services;

import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.api.DAO.VoucherDAO;
import com.group2.local.LoginManagerTemp;
import com.group2.model.Artist;
import com.group2.model.ResponseValidate;
import com.group2.model.Voucher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class VoucherService {
    public static final VoucherService instance = new VoucherService();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final IVoucherService service = ServiceGenerator.createService(IVoucherService.class);

    public CompletableFuture<ResponseValidate> validateVoucher(String voucherCode) {
        CompletableFuture<ResponseValidate> future = new CompletableFuture<>();
        String authHeader = "Bearer " + LoginManagerTemp.token;
        executor.execute(() -> {
            Call<ValidationResponseDAO> call = service.validateVoucher(authHeader, voucherCode);
            try {
                Response<ValidationResponseDAO> response = call.execute();
                if (response.isSuccessful()) {
                    ValidationResponseDAO validationResponse = response.body();
                    if (validationResponse == null) {
                        future.completeExceptionally(new NullPointerException("Voucher not found")); // Complete the future exceptionally if voucher is null
                        return;
                    }
                    future.complete(validationResponse.asResponseValidate()); // Complete the future with the ValidationResponseDAO object
                } else {
                    future.completeExceptionally(new Exception("Voucher Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }

    public CompletableFuture<ArrayList<Voucher>> getListVoucher() {
        CompletableFuture<ArrayList<Voucher>> future = new CompletableFuture<>();
        String authHeader = "Bearer " + LoginManagerTemp.token;
        executor.execute(() -> {
            Call<Collection<VoucherDAO>> call = service.getListVoucher(authHeader);
            try {
                Response<Collection<VoucherDAO>> response = call.execute();
                if (response.isSuccessful()) {
                    Collection<VoucherDAO> voucherList = response.body();
                    if (voucherList == null) {
                        future.completeExceptionally(new NullPointerException("Voucher not found")); // Complete the future exceptionally if voucher is null
                        return;
                    }
                    ArrayList<Voucher> vouchers = new ArrayList<>();
                    for (VoucherDAO voucher : voucherList) {
                        vouchers.add(voucher.asVoucher());
                    }
                    future.complete(vouchers); // Complete the future with the Collection<VoucherDAO> object
                } else {
                    future.completeExceptionally(new Exception("Voucher Network Request Error: " + response.code())); // Complete the future exceptionally if the response is not successful
                }
            } catch (IOException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an IOException occurs
            }
        });
        return future;
    }
}
