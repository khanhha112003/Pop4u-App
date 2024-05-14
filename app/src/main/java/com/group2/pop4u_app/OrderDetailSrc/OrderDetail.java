package com.group2.pop4u_app.OrderDetailSrc;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.group2.adapter.OrderAdapter;
import com.group2.adapter.OrderDetailAdapter;
import com.group2.model.Order;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

public class OrderDetail extends AppCompatActivity {
    ActivityOrderDetailBinding binding;
    OrderAdapter orderAdapter;
    ArrayList<Order> orders;
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
        orders = new ArrayList<>();
        orders.add(new Order("ABC", "https://cdn-contents.weverseshop.io/public/shop/3922e5e598bf800360b3d3a47e1969d9.png?q=95&w=720", "ALBUm", "BLACKPINK", 30000, 3));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetail.this, LinearLayoutManager.VERTICAL, false);
        ItemOffsetVerticalRecycler itemOffsetVerticalRecycler = new ItemOffsetVerticalRecycler(OrderDetail.this, R.dimen.item_offset);
        binding.rvOrderList.setNestedScrollingEnabled(false);
        binding.rvOrderList.setLayoutManager(linearLayoutManager);
        binding.rvOrderList.addItemDecoration(itemOffsetVerticalRecycler);

        orderAdapter = new OrderAdapter(OrderDetail.this, orders);

        binding.rvOrderList.setAdapter(orderAdapter);
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
}