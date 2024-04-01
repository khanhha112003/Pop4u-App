package com.group2.pop4u_app.PaymentScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.group2.adapter.OrderAdapter;
import com.group2.model.CartItem;
import com.group2.model.Order;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityPaymentBinding;

import java.text.DecimalFormat;
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
        calculatetotalPriceOrder();

    }
    private void customAndLoadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false);
        binding.rvOrder.setLayoutManager(layoutManager);
//        binding.rvCart.setHasFixedSize(true);

        orders = new ArrayList<>();
        orders.add(new Order(R.drawable.photo_ex, "The Album - BlackPink", "Artist", "Hồng", 400000, 3));
        orders.add(new Order(R.drawable.photo_ex, "The Album - BlackPink", "Artist", "Hồng", 400000, 1));
        orders.add(new Order(R.drawable.photo_ex, "The Album - BlackPink", "Artist", "Hồng", 400000, 1));


        adapter = new OrderAdapter(getApplicationContext(), orders);
        binding.rvOrder.setAdapter(adapter);
    }

        private void calculatetotalPriceOrder() {
            double totalPriceOrder = 0;
            for (Order item : orders) {
                totalPriceOrder += item.getO_price() * item.getO_quantity();
            }

            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedtotalPriceOrder = decimalFormat.format(totalPriceOrder);

            // Hiển thị tổng thanh toán đã được định dạng trong TextView totalPrice
            binding.totalPriceOrder.setText(formattedtotalPriceOrder);
        }
    }