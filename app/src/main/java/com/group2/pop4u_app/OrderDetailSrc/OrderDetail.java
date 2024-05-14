package com.group2.pop4u_app.OrderDetailSrc;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.group2.adapter.OrderAdapter;
import com.group2.adapter.OrderDetailAdapter;
import com.group2.api.Services.OrderService;
import com.group2.model.Order;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class OrderDetail extends AppCompatActivity {
    ActivityOrderDetailBinding binding;
    OrderAdapter orderAdapter;
    ArrayList<Order> orders = new ArrayList<>();
    com.group2.model.OrderDetail orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrToolbar);
        getSupportActionBar().setTitle("Thông tin đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        loadData();
    }

    private void loadData() {
        String orderID = getIntent().getStringExtra("orderID");
        if (orderID == null) {
            finish();
            return;
        }
        CompletableFuture<com.group2.model.OrderDetail> future = OrderService.instance.getOrderDetail(orderID);
        future.thenAccept(orderDetail -> {
            this.orderDetail = orderDetail;
            for (com.group2.model.CartItem cartItem : orderDetail.getCartItems()) {
                orders.add(new Order(cartItem.getProductCode(), cartItem.getThumb(), cartItem.getName(), "", cartItem.getPrice(), cartItem.getQuantity()));
            }
            setrv();
            runOnUiThread(() -> {
                binding.txtOrderID.setText(orderDetail.getOrderCode());
                binding.txtOrderDate.setText(orderDetail.getOrderDate());
                binding.oTotalPrice.setText(String.valueOf(orderDetail.getTotalPrice()));
                binding.CusAddress.setText(orderDetail.getAddress());
                binding.CusPhone.setText(orderDetail.getPhone());
                binding.tvVoucher.setText(orderDetail.getCouponCode());
                binding.txtPaymentMethod.setText(orderDetail.getPaymentMethod());
            });
        });
        try {
            future.get();
        } catch (Exception e) {
            Log.d("OrderDetail", "loadData: " + e.getMessage());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plain_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setrv(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetail.this, LinearLayoutManager.VERTICAL, false);
        ItemOffsetVerticalRecycler itemOffsetVerticalRecycler = new ItemOffsetVerticalRecycler(OrderDetail.this, R.dimen.item_offset);
        binding.rvOrderList.setNestedScrollingEnabled(false);
        binding.rvOrderList.setLayoutManager(linearLayoutManager);
        binding.rvOrderList.addItemDecoration(itemOffsetVerticalRecycler);
        orderAdapter = new OrderAdapter(OrderDetail.this, orders);
        binding.rvOrderList.setAdapter(orderAdapter);
    }
}