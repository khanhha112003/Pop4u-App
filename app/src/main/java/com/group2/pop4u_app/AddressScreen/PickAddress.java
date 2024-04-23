package com.group2.pop4u_app.AddressScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.group2.adapter.AddressAdapter;
import com.group2.model.Address;
import com.group2.pop4u_app.PaymentScreen.Payment;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.VoucherScreen.ShowVoucher;
import com.group2.pop4u_app.databinding.ActivityPickAddressBinding;


import java.util.ArrayList;

public class PickAddress extends AppCompatActivity {
ActivityPickAddressBinding binding;
    AddressAdapter adapter;
    ArrayList<Address> addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPickAddressBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrPickAddress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        initData();
        loadData();
        addEvents();
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
    private  void initData(){
        addresses = new ArrayList<>();
        addresses.add(new Address("Khánh Hà", "0123455777", "Cổng sau KTX khu B, Phường Đông Hòa, Thành phố Dĩ An, tỉnh Bình Dương"));
        addresses.add(new Address("Kahane", "0567855777", "669 QL1A, khu phố 3, Thủ Đức, Thành phố Hồ Chí Minh"));
        addresses.add(new Address("Ka Ka ", "098755777", "669 QL1A, khu phố 3, Thủ Đức, Thành phố Hồ Chí Minh"));
    }
    private void loadData(){
        adapter = new AddressAdapter(PickAddress.this, R.layout.activity_item_address, addresses);
        binding.lvAddress.setAdapter(adapter);
    }
    private void addEvents() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickAddress.this, AddAddress.class);
                startActivity(intent);
            }
        });
    }
}