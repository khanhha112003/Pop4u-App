package com.group2.pop4u_app.CartScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.group2.model.CartItem;
import com.group2.pop4u_app.databinding.ActivityItemCartBinding;

public class Item_Cart extends AppCompatActivity {

    ActivityItemCartBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CartItem item = getItemFromBundle();

        // Nếu mục hàng tồn tại
        if (item != null) {
            // Thiết lập trạng thái checkbox dựa trên isChecked của mục hàng
            binding.checkbox.setChecked(item.isChecked());

            // Xử lý sự kiện khi checkbox được nhấn
            binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Cập nhật trạng thái isChecked của mục hàng khi checkbox thay đổi
                    item.setChecked(isChecked);
                }
            });
        }
    }

    // Phương thức để lấy mục hàng từ Bundle
    private CartItem getItemFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return (CartItem) bundle.getSerializable("cart_item");
        }
        return null;
    }
}