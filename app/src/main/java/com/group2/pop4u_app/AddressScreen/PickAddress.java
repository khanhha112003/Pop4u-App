package com.group2.pop4u_app.AddressScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group2.adapter.AddressAdapter;
import com.group2.model.Address;
import com.group2.pop4u_app.R;
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
        setContentView(binding.getRoot());
        initData();
        loadData();
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

}