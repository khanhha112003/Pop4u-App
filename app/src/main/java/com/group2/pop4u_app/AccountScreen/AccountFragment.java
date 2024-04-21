package com.group2.pop4u_app.AccountScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.adapter.SettingListAdapter;
import com.group2.api.Services.UserService;
import com.group2.model.SettingItem;
import com.group2.model.User;
import com.group2.pop4u_app.AddressScreen.PickAddress;
import com.group2.pop4u_app.MainActivity;
import com.group2.pop4u_app.OrderScreen.OrderScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentAccountBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;
    SettingListAdapter settingAccountListAdapter, settingSystemListAdapter;
    ArrayList<SettingItem> settingAccountItems, settingSystemItems;
    SettingItem selectedItem;
    User user = new User("username", "email", "name", "birthdate", "phone_number");
    public AccountFragment() { }
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        initData1();
        initData2();
        addEvents();
        CompletableFuture<User> userInfoFuture = UserService.instance.getUserProfile();
        userInfoFuture.thenAccept(user -> {
            this.user = user;
            this.setUserProfile();
        });
        try {
            userInfoFuture.get();
        } catch (Exception e) {
            Log.d("AccountFragment", "Get user info failed");
        }
        return binding.getRoot();
    }

    private void addEvents() {
        settingAccountListAdapter.setOnClickListener((position, settingItem) -> {
            switch (settingItem.getSettingID()) {
                case "order":
                case "payment": {
                    Intent intent = new Intent(requireActivity(), OrderScreen.class);
                    startActivity(intent);
                    break;
                }
                case "address": {
                    Intent intent = new Intent(requireActivity(), PickAddress.class);
                    startActivity(intent);
                    break;
                }
                default:
                    selectedItem = settingItem;
                    startSettingScreen();
                    break;
            }
        });

        settingSystemListAdapter.setOnClickListener((position, settingItem) -> {
            selectedItem = settingItem;
            startSettingScreen();
        });

        binding.btnSignOut.setOnClickListener(v -> {
            CompletableFuture<Boolean> future = UserService.instance.logout();
            future.thenAccept(result -> {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (result && mainActivity != null) {
                    mainActivity.backHome();
                } else {
                    Log.d("AccountFragment", "Logout failed");
                }
            });
            try {
                future.get();
            } catch (Exception e) {
                Log.d("AccountFragment", "Logout failed");
            }
        });
    }

    private void startSettingScreen() {
        Intent intent = new Intent(requireActivity(), SettingScreen.class);
        intent.putExtra("settingItem", selectedItem);
        startActivity(intent);
    }

    private void initData2() {
        LinearLayoutManager linearLayoutManagerSystem = new LinearLayoutManager(requireActivity());
        binding.rcvSystemSetting.setLayoutManager(linearLayoutManagerSystem);
        binding.rcvSystemSetting.setHasFixedSize(true);

        settingSystemItems = new ArrayList<>();
        settingSystemItems.add(new SettingItem("notification", R.drawable.notifications_unread, "Tùy chỉnh thông báo"));
        settingSystemItems.add(new SettingItem("website", R.drawable.globe, "Pop4u Website"));
        settingSystemItems.add(new SettingItem("termsAndPolicy", R.drawable.lists, "Chính sách"));
        settingSystemItems.add(new SettingItem("helpAndReport", R.drawable.help, "Giúp đỡ và khiếu nại"));
        settingSystemListAdapter = new SettingListAdapter(getActivity(), settingSystemItems);
        binding.rcvSystemSetting.setAdapter(settingSystemListAdapter);
        binding.rcvSystemSetting.setNestedScrollingEnabled(false);
    }

    private void initData1() {
        LinearLayoutManager linearLayoutManagerAccount = new LinearLayoutManager(requireActivity());
        binding.rcvAccountSetting.setLayoutManager(linearLayoutManagerAccount);
        binding.rcvAccountSetting.setHasFixedSize(true);

        settingAccountItems = new ArrayList<>();
        settingAccountItems.add(new SettingItem("personal_info", R.drawable.contacts, "Thông tin cá nhân"));
        settingAccountItems.add(new SettingItem("order_history", R.drawable.quick_reorder, "Lịch sử đơn hàng"));
        settingAccountItems.add(new SettingItem("address_list", R.drawable.explore_nearby, "Sổ địa chỉ"));
        settingAccountItems.add(new SettingItem("payment_method", R.drawable.account_balance_wallet, "Phương thức thanh toán"));
        settingAccountListAdapter = new SettingListAdapter(requireActivity(), settingAccountItems);
        binding.rcvAccountSetting.setAdapter(settingAccountListAdapter);
        binding.rcvAccountSetting.setNestedScrollingEnabled(false);
    }

    private void setUserProfile() {
        binding.txtUserFullName.setText(user.getFullname());
        binding.txtUserEmail.setText(user.getEmail());
    }

}
