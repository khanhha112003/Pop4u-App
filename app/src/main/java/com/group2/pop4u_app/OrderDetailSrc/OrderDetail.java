package com.group2.pop4u_app.OrderDetailSrc;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.group2.adapter.OrderAdapter;
import com.group2.adapter.OrderDetailAdapter;
import com.group2.model.Order;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

public class OrderDetail extends AppCompatActivity {
ActivityOrderDetailBinding binding;
OrderDetailAdapter adapter;
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
//        orders = new ArrayList<>();
//        adapter = new OrderDetailAdapter(OrderDetail.this, orders);
        binding.rvOrderList.setAdapter(adapter);
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