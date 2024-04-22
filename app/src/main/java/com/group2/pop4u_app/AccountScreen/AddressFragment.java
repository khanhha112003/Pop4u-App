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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment {
    FragmentAddressBinding binding;
    AddressAdapter adapter;
    ArrayList<Address> addresses;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
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
        binding = FragmentAddressBinding.inflate(inflater,container,false);
        initData();
        loadData();
        addEvents();
        return binding.getRoot();

    }

    private void initData() {
        addresses = new ArrayList<>();
        addresses.add(new Address("Khánh Hà", "0123455777", "Cổng sau KTX khu B, Phường Đông Hòa, Thành phố Dĩ An, tỉnh Bình Dương"));
        addresses.add(new Address("Kahane", "0567855777", "669 QL1A, khu phố 3, Thủ Đức, Thành phố Hồ Chí Minh"));
        addresses.add(new Address("Ka Ka ", "098755777", "669 QL1A, khu phố 3, Thủ Đức, Thành phố Hồ Chí Minh"));
    }

    private void loadData() {
        adapter = new AddressAdapter((Activity) requireContext(), R.layout.activity_item_address, addresses);
        binding.lvAddress.setAdapter(adapter);
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