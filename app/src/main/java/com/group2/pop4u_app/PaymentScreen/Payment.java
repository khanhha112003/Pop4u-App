package com.group2.pop4u_app.PaymentScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.group2.pop4u_app.CartScreen.adapter.CartAdapter;
import com.group2.pop4u_app.CartScreen.model.CartItem;
import com.group2.pop4u_app.PaymentScreen.adapter.OrderAdapter;
import com.group2.pop4u_app.PaymentScreen.model.Order;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityPaymentBinding;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {
ActivityPaymentBinding binding;
    OrderAdapter adapter;
    ArrayList<Order> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        customAndLoadData();

    }
    private void customAndLoadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (this,LinearLayoutManager.VERTICAL, false);
        binding.rvOrder.setLayoutManager(layoutManager);
//        binding.rvCart.setHasFixedSize(true);

        orders = new ArrayList<>();
        orders.add(new Order(R.drawable.photo_ex, "The Album - BlackPink","Artist", "Hồng", 400000, 3));
        orders.add(new Order(R.drawable.photo_ex, "The Album - BlackPink","Artist", "Hồng", 400000, 3));
        orders.add(new Order(R.drawable.photo_ex, "The Album - BlackPink","Artist", "Hồng", 400000, 3));


        adapter = new OrderAdapter(getApplicationContext(), orders);
        binding.rvOrder.setAdapter(adapter);
    }
}