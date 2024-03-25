package com.group2.pop4u_app.VoucherScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.VoucherScreen.adapter.VoucherAdapter;
import com.group2.pop4u_app.VoucherScreen.model.ItemVoucher;
import com.group2.pop4u_app.databinding.ActivityShowVoucherBinding;

import java.util.ArrayList;

public class ShowVoucher extends AppCompatActivity {
ActivityShowVoucherBinding binding;
    VoucherAdapter adapter;
    ArrayList<ItemVoucher> vouchers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        loadData();

    }
    private  void initData(){
        vouchers = new ArrayList<>();
        vouchers.add(new ItemVoucher("POP4U12345", "Giảm 10% giảm tối đa 20K "));
        vouchers.add(new ItemVoucher("POP4U56789", "Giảm 20% giảm tối đa 15K"));
        vouchers.add(new ItemVoucher("POP4U12111", "Freeship"));

    }
    private void loadData(){
        adapter = new VoucherAdapter(ShowVoucher.this, R.layout.activity_item_voucher, vouchers);
        binding.lvVoucher.setAdapter(adapter);
    }

}