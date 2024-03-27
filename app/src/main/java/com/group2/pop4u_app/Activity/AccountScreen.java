package com.group2.pop4u_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.group2.adapter.SettingListAdapter;
import com.group2.model.SettingItem;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityAccountScreenBinding;

import java.util.ArrayList;

public class AccountScreen extends AppCompatActivity {

    ActivityAccountScreenBinding binding;

    SettingListAdapter settingAccountListAdapter, settingSystemListAdapter;

    ArrayList<SettingItem> settingAccountItems, settingSystemItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvAccountSetting.setLayoutManager(linearLayoutManager);
        binding.rcvAccountSetting.setHasFixedSize(true);

        settingAccountItems = new ArrayList<>();
        settingAccountItems.add(new SettingItem("1", R.drawable.contacts, "Thông tin cá nhân"));
        settingAccountItems.add(new SettingItem("1", R.drawable.quick_reorder, "Lịch sử đơn hàng"));
        settingAccountItems.add(new SettingItem("1", R.drawable.explore_nearby, "Sổ địa chỉ"));
        settingAccountItems.add(new SettingItem("1", R.drawable.account_balance_wallet, "Phương thức thanh toán"));
        settingAccountListAdapter = new SettingListAdapter(this, settingAccountItems);
        binding.rcvAccountSetting.setAdapter(settingAccountListAdapter);
    }
}