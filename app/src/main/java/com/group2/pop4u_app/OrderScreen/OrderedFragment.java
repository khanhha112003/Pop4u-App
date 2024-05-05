package com.group2.pop4u_app.OrderScreen;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.group2.adapter.OrderDetailAdapter;
import com.group2.model.Order;
import com.group2.pop4u_app.databinding.FragmentOrderedBinding;

import java.util.ArrayList;
public class OrderedFragment extends Fragment {

    FragmentOrderedBinding binding;
    OrderDetailAdapter orderDetailAdapter;
    ArrayList<Order> orderDetailItem;

    public OrderedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentOrderedBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        //binding.lvOrderedList.setLayoutManager(layoutManager);


        orderDetailItem = new ArrayList<>();
        orderDetailItem.add(new Order("1", "", "The Album - BlackPink", "Black Pink", 400000, 1));
        orderDetailItem.add(new Order("1", "", "The Album - BlackPink", "Black Pink", 400000, 1));
        orderDetailItem.add(new Order("1", "", "The Album - BlackPink", "Black Pink", 400000, 1));

        orderDetailAdapter = new OrderDetailAdapter(orderDetailItem);
//        binding.lvOrderedList.setAdapter((ListAdapter) orderDetailAdapter);
    }
}