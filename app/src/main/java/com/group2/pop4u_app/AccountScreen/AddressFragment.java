package com.group2.pop4u_app.AccountScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.adapter.AddressAdapter;
import com.group2.model.Address;
import com.group2.pop4u_app.AddressScreen.AddAddress;
import com.group2.pop4u_app.AddressScreen.PickAddress;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentAddressBinding;

import java.util.ArrayList;


public class AddressFragment extends Fragment {
    FragmentAddressBinding binding;
    AddressAdapter adapter;
    ArrayList<Address> addresses = new ArrayList<>();


    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddressBinding.inflate(inflater,container,false);
        loadData();
        addEvents();
        return binding.getRoot();

    }

    private void loadData() {
        adapter = new AddressAdapter((Activity) requireContext(), R.layout.activity_item_address, addresses);
//        binding.lvAddress.setAdapter(adapter);
    }

    private void addEvents() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddAddress.class);
                startActivity(intent);
            }
        });
    }
}