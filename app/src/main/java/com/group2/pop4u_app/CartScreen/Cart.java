package com.group2.pop4u_app.CartScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.group2.pop4u_app.CartScreen.adapter.CartAdapter;
import com.group2.pop4u_app.CartScreen.model.CartItem;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityCartScreenBinding;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ActivityCartScreenBinding binding;
    CartAdapter adapter;
    ArrayList<CartItem> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        customAndLoadData();
        binding.checkboxSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            adapter.selectAllItems(isChecked);
            calculateTotalPrice();
        });
    }
    private void customAndLoadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (this,LinearLayoutManager.VERTICAL, false);
        binding.rvCart.setLayoutManager(layoutManager);
//        binding.rvCart.setHasFixedSize(true);

        carts = new ArrayList<>();
        carts.add(new CartItem(R.drawable.photo_ex, "The Album - BlackPink","Hồng", "400.000", "12"));
        carts.add(new CartItem(R.drawable.photo_ex, "The Album - BlackPink","Hồng", "400.000", "12"));
        carts.add(new CartItem(R.drawable.photo_ex, "The Album - BlackPink","Hồng", "400.000", "12"));
        carts.add(new CartItem(R.drawable.photo_ex, "The Album - BlackPink","Hồng", "400.000", "12"));


        adapter = new CartAdapter(getApplicationContext(), carts);
        binding.rvCart.setAdapter(adapter);

        adapter.setOnQuantityChangeListener(new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityDecrease(int position) {
                // Xử lý giảm số lượng sản phẩm
                decreaseQuantity(position);
            }

            @Override
            public void onQuantityIncrease(int position) {
                // Xử lý tăng số lượng sản phẩm
                increaseQuantity(position);
            }
        });
    }

    // Xử lý giảm số lượng sản phẩm
    private void decreaseQuantity(int position) {
        CartItem item = carts.get(position);
        int currentQuantity = Integer.parseInt(item.getQuantity());
        if (currentQuantity > 1) {
            currentQuantity--;
            item.setQuantity(String.valueOf(currentQuantity));
            adapter.notifyItemChanged(position);
        }
    }

    // Xử lý tăng số lượng sản phẩm
    private void increaseQuantity(int position) {
        CartItem item = carts.get(position);
        int currentQuantity = Integer.parseInt(item.getQuantity());
        currentQuantity++;
        item.setQuantity(String.valueOf(currentQuantity));
        adapter.notifyItemChanged(position);
    }
    private void calculateTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : carts) {
            if (item.isChecked()) {
                totalPrice += Double.parseDouble(item.getPrice()) * Integer.parseInt(item.getQuantity());
            }
        }
        binding.textView.setText(String.valueOf(totalPrice));
    }
}