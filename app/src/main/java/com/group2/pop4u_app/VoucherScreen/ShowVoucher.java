package com.group2.pop4u_app.VoucherScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.group2.api.Services.VoucherService;
import com.group2.model.Voucher;
import com.group2.adapter.VoucherAdapter;
import com.group2.model.ItemVoucher;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityShowVoucherBinding;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ShowVoucher extends AppCompatActivity {
    ActivityShowVoucherBinding binding;
    VoucherAdapter adapter;
    ArrayList<ItemVoucher> vouchers;

    ArrayList<Voucher> vouchersData;
    SearchView editTextSearch;
    ListView listViewVoucher;
    LinearLayout textViewNoVoucher;
    private ItemVoucher selectedVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowVoucherBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrInputVoucher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());

        editTextSearch = binding.svQuerySearchBox;
        listViewVoucher = binding.lvVoucher;
        textViewNoVoucher = binding.layoutNoVoucher;
        initData();
        setupSearch();
        Button btnApplySearch = findViewById(R.id.btnApplySearch);
        btnApplySearch.setOnClickListener(v -> {
            // Kiểm tra xem có voucher nào được chọn không
            for (ItemVoucher item : vouchers) {
                if (item.isSelected()) {
                    selectedVoucher = item; // Lưu trữ voucher được chọn
                    break;
                }
            }

            // Nếu có voucher được chọn, chuyển đến màn hình thanh toán
            if (selectedVoucher != null) {
                int count = 0;
                for (ItemVoucher item : vouchers) {
                    if (Objects.equals(item.getVoucher_id(), selectedVoucher.getVoucher_id())) {
                        break;
                    }
                    count++;
                }
                Voucher choosenVoucher = vouchersData.get(count);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("voucher", choosenVoucher);
                intent.putExtra("data", bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                // Nếu không có voucher nào được chọn, hiển thị toast
                Toast.makeText(ShowVoucher.this, "Vui lòng chọn voucher trước.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plain_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData(){
        vouchers = new ArrayList<>();
        CompletableFuture<ArrayList<Voucher>> future = VoucherService.instance.getListVoucher();
        future.thenAccept(voucherList -> {
            for (Voucher voucher : voucherList) {
                vouchers.add(new ItemVoucher(voucher.getCode(), voucher.getDescription()));
            }
            vouchersData = voucherList;
            runOnUiThread(() -> {
                if (vouchers.isEmpty()) {
                    showNoVoucher();
                } else {
                    showVoucher();
                }
            });
        }).exceptionally(throwable -> {
            runOnUiThread(() -> {
                Toast.makeText(ShowVoucher.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            });
            return null;
        });

        try {
           future.get();
        } catch (Exception e) {
            Log.d("ShowVoucher", "Error: " + e.getMessage());
        }
    }

    private void showVoucher() {
        // Hiển thị danh sách voucher
        listViewVoucher.setVisibility(View.VISIBLE);
        textViewNoVoucher.setVisibility(View.GONE);
        // Tạo adapter mới với danh sách voucher
        adapter = new VoucherAdapter(ShowVoucher.this, R.layout.activity_item_voucher, vouchers);
        listViewVoucher.setAdapter(adapter);
    }

    private void showNoVoucher() {
        // Hiển thị layout "Không có voucher"
        listViewVoucher.setVisibility(View.GONE);
        textViewNoVoucher.setVisibility(View.VISIBLE);
    }

    private void setupSearch() {
        editTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        ArrayList<ItemVoucher> filteredList = new ArrayList<>();
        for (ItemVoucher voucher : vouchers) {
            if (voucher.getVoucher_id().toLowerCase().contains(text.toLowerCase())
                    || voucher.getVoucher_description().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(voucher);
            }
        }
        if (filteredList.isEmpty()) {
            // Hiển thị layout "Không có voucher" nếu danh sách lọc rỗng
            listViewVoucher.setVisibility(View.GONE);
            textViewNoVoucher.setVisibility(View.VISIBLE);
        } else {
            // Hiển thị danh sách voucher nếu danh sách lọc không rỗng
            listViewVoucher.setVisibility(View.VISIBLE);
            textViewNoVoucher.setVisibility(View.GONE);
            // Tạo adapter mới với danh sách voucher lọc
            adapter = new VoucherAdapter(ShowVoucher.this, R.layout.activity_item_voucher, filteredList);
            listViewVoucher.setAdapter(adapter);
        }
    }
}
