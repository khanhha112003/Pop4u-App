package com.group2.pop4u_app.OrderScreen;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.group2.adapter.OrderDetailAdapter;
import com.group2.api.Services.OrderService;
import com.group2.model.CartItem;
import com.group2.model.OrderDetail;
import com.group2.pop4u_app.AccountScreen.AccountFragment;
import com.group2.pop4u_app.HomeScreen.FavoriteListActivity;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.FragmentOrderedBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class OrderedFragment extends Fragment {

    FragmentOrderedBinding binding;
    OrderDetailAdapter orderDetailAdapter;
    ArrayList<OrderDetail> orderList;

    String param = "Pending";

    public OrderedFragment(String param) {
        // Required empty public constructor
        if (param != null) {
            this.param = param;
        }
    }
    public static OrderedFragment newInstance() {
        OrderedFragment fragment = new OrderedFragment(null);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderedBinding.inflate(inflater, container, false);
        setupRecyclerView();
        addEvents();
        getData();
        return binding.getRoot();
    }

    private void addEvents() {
        orderDetailAdapter.setOnClickListener((position, order) -> {
            Intent intent = new Intent(requireActivity(), com.group2.pop4u_app.OrderDetailSrc.OrderDetail.class);
            intent.putExtra("orderID", order.getId());
            startActivity(intent);
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

        orderDetailAdapter = new OrderDetailAdapter(requireActivity(), orderList);
        binding.rvOrderedList.setAdapter(orderDetailAdapter);
    }

    private void getData() {
        // Call API to get data
        CompletableFuture<ArrayList<OrderDetail>> future = OrderService.instance.getListOrder(param);
        future.thenAccept(orderDetails -> {
            orderList.clear();
            orderList.addAll(orderDetails);
            orderDetailAdapter.notifyDataSetChanged();
        });
        try {
            future.get();
        } catch (Exception e) {
            Log.d("OrderedFragment", "getData: " + e.getMessage());
        }
    }

}
