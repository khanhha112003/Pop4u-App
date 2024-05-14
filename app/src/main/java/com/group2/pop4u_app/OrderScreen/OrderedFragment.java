package com.group2.pop4u_app.OrderScreen;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.group2.adapter.OrderDetailAdapter;
import com.group2.model.CartItem;
import com.group2.model.OrderDetail;
import com.group2.pop4u_app.AccountScreen.AccountFragment;
import com.group2.pop4u_app.HomeScreen.FavoriteListActivity;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentOrderedBinding;

import java.util.ArrayList;

public class OrderedFragment extends Fragment {

    FragmentOrderedBinding binding;
    OrderDetailAdapter orderDetailAdapter;
    ArrayList<OrderDetail> orderList;

    public OrderedFragment() {
        // Required empty public constructor
    }
    public static OrderedFragment newInstance() {
        OrderedFragment fragment = new OrderedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderedBinding.inflate(inflater, container, false);
        setupRecyclerView();
        addEvents();
        return binding.getRoot();
    }

    private void addEvents() {
        orderDetailAdapter.setOnClickListener(new OrderDetailAdapter.OnClickListener() {
            @Override
            public void OnClick(int position, OrderDetail order) {
                Intent intent = new Intent(requireActivity(), com.group2.pop4u_app.OrderDetailSrc.OrderDetail.class);
                intent.putExtra("orderID", order.getId());
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {
        if (getContext() == null) return;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        ItemOffsetVerticalRecycler itemOffsetVerticalRecycler = new ItemOffsetVerticalRecycler(requireContext(), R.dimen.item_offset);
        binding.rvOrderedList.setLayoutManager(linearLayoutManager);
        binding.rvOrderedList.addItemDecoration(itemOffsetVerticalRecycler);
        binding.rvOrderedList.setHasFixedSize(true);

        orderList = new ArrayList<>();

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(new CartItem("P123", "url_to_image", "Product Name", 10000, 12000, 2, true));

        OrderDetail orderDetail = new OrderDetail("120124KCMS", "bumr2405", 750000, "2024-05-13", "Processing", "123 Elm St", "555-1234", true, false, "Credit Card", items, 50000, "SUMMER2024", "ORD001", "Leave at door if not home.");
        orderList.add(new OrderDetail("120124KCMS", "bumr2405", 750000, "2024-05-13", "Processing", "123 Elm St", "555-1234", true, false, "Credit Card", items, 50000, "SUMMER2024", "ORD001", "Leave at door if not home."));
        orderList.add(orderDetail);

        orderDetailAdapter = new OrderDetailAdapter(requireActivity(), orderList);
        binding.rvOrderedList.setAdapter(orderDetailAdapter);
    }


}
