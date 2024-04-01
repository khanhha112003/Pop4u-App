package com.group2.adapter.AccountScreen;

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

//        loadData();
//        addEvents();
    }

    private void addEvents() {
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManagerAccount = new LinearLayoutManager(this);
        binding.rcvAccountSetting.setLayoutManager(linearLayoutManagerAccount);
        binding.rcvAccountSetting.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerSystem = new LinearLayoutManager(this);
        binding.rcvSystemSetting.setLayoutManager(linearLayoutManagerSystem);
        binding.rcvSystemSetting.setHasFixedSize(true);

        settingAccountItems = new ArrayList<>();
        settingAccountItems.add(new SettingItem("1", R.drawable.contacts, "Thông tin cá nhân"));
        settingAccountItems.add(new SettingItem("1", R.drawable.quick_reorder, "Lịch sử đơn hàng"));
        settingAccountItems.add(new SettingItem("1", R.drawable.explore_nearby, "Sổ địa chỉ"));
        settingAccountItems.add(new SettingItem("1", R.drawable.account_balance_wallet, "Phương thức thanh toán"));
        settingAccountListAdapter = new SettingListAdapter(this, settingAccountItems);
        binding.rcvAccountSetting.setAdapter(settingAccountListAdapter);

        settingSystemItems = new ArrayList<>();
        settingSystemItems.add(new SettingItem("notification", R.drawable.notifications_unread, "Tùy chỉnh thông báo"));
        settingSystemItems.add(new SettingItem("language", R.drawable.globe, "Ngôn ngữ"));
        settingSystemItems.add(new SettingItem("termsAndPolicy", R.drawable.lists, "Chính sách"));
        settingSystemItems.add(new SettingItem("helpAndReport", R.drawable.help, "Giúp đỡ và khiếu nại"));
        settingSystemListAdapter = new SettingListAdapter(this, settingSystemItems);
        binding.rcvSystemSetting.setAdapter(settingSystemListAdapter);
    }
}