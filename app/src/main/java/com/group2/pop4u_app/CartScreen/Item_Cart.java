package com.group2.pop4u_app.CartScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.group2.pop4u_app.CartScreen.model.CartItem;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityItemCartBinding;

public class Item_Cart extends AppCompatActivity {

ActivityItemCartBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem item = getItemFromBundle();
                if (item != null) {
                    item.setChecked(binding.checkbox.isChecked());
                }
            }
        });
    }

    private CartItem getItemFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return (CartItem) bundle.getSerializable("cart_item");
        }
        return null;
    }
}