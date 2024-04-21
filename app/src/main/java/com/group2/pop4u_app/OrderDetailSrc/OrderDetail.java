package com.group2.pop4u_app.OrderDetailSrc;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.adapter.OrderAdapter;
import com.group2.model.Order;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

public class OrderDetail extends AppCompatActivity {
ActivityOrderDetailBinding binding;
OrderAdapter adapter;
ArrayList <Order> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
    }

    private void loadData() {
    orders = new ArrayList<>();
//        adapter = new OrderAdapter(OrderDetail.this, R.layout.activity_item_order, orders);
        binding.lvOrdered.setAdapter(adapter);
    }
}