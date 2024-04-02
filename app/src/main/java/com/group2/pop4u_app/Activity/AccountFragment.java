package com.group2.pop4u_app.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.adapter.SettingListAdapter;
import com.group2.model.SettingItem;
import com.group2.pop4u_app.AccountScreen.SettingScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentAccountBinding;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    SettingListAdapter settingAccountListAdapter, settingSystemListAdapter;

    ArrayList<SettingItem> settingAccountItems, settingSystemItems;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData1();
//        initData2();
        addEvents();
    }


    private void addEvents() {
        settingAccountListAdapter.setOnClickListener(new SettingListAdapter.OnClickListener() {
            @Override
            public void onClick(int position, SettingItem settingItem) {
                Intent intent = new Intent(requireActivity(), SettingScreen.class);
                intent.putExtra("settingItem", (Serializable) settingItem);
                startActivity(intent);
            }
        });
    }

    private void initData2() {
        LinearLayoutManager linearLayoutManagerSystem = new LinearLayoutManager(requireActivity());
        binding.rcvSystemSetting.setLayoutManager(linearLayoutManagerSystem);
        binding.rcvSystemSetting.setHasFixedSize(true);
        binding.rcvSystemSetting.setNestedScrollingEnabled(false);

        settingSystemItems = new ArrayList<>();
        settingSystemItems.add(new SettingItem("notification", R.drawable.notifications_unread, "Tùy chỉnh thông báo"));
        settingSystemItems.add(new SettingItem("language", R.drawable.globe, "Ngôn ngữ"));
        settingSystemItems.add(new SettingItem("termsAndPolicy", R.drawable.lists, "Chính sách"));
        settingSystemItems.add(new SettingItem("helpAndReport", R.drawable.help, "Giúp đỡ và khiếu nại"));
        settingSystemListAdapter = new SettingListAdapter(requireActivity(), settingSystemItems);
        binding.rcvSystemSetting.setAdapter(settingSystemListAdapter);
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

}
