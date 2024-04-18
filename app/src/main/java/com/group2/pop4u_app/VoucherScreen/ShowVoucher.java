package com.group2.pop4u_app.VoucherScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.group2.pop4u_app.PaymentScreen.Payment;
import com.group2.pop4u_app.R;
import com.group2.adapter.VoucherAdapter;
import com.group2.model.ItemVoucher;
import com.group2.pop4u_app.databinding.ActivityShowVoucherBinding;

import java.util.ArrayList;

public class ShowVoucher extends AppCompatActivity {
    ActivityShowVoucherBinding binding;
    VoucherAdapter adapter;
    ArrayList<ItemVoucher> vouchers;
    SearchView editTextSearch;
    ListView listViewVoucher;
    LinearLayout textViewNoVoucher;
    ImageView backButton;

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
        vouchers.add(new ItemVoucher("POP4U12345", "Giảm 10% giảm tối đa 20K "));
        vouchers.add(new ItemVoucher("POP4U56789", "Giảm 20% giảm tối đa 15K"));
        vouchers.add(new ItemVoucher("POP4U12111", "Freeship"));

        // Ẩn danh sách voucher và hiển thị layout "Không có voucher"
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
