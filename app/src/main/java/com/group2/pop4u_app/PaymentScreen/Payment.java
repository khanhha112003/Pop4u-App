package com.group2.pop4u_app.PaymentScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.group2.adapter.OrderAdapter;
import com.group2.model.Order;
import com.group2.pop4u_app.AddressScreen.PickAddress;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.VoucherScreen.ShowVoucher;
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
        setSupportActionBar(binding.tbrPayment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        customAndLoadData();
        calculatetotalPriceOrder();
        addEvents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plain_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Dialog dialog = new Dialog(Payment.this);
            dialog.setContentView(R.layout.dialog_quit_order_flow);
            Button btnQuit, btnContinue;
            ImageButton btnCloseDialog;
            btnQuit = dialog.findViewById(R.id.btnQuit);
            btnContinue = dialog.findViewById(R.id.btnContinue);
            btnCloseDialog = dialog.findViewById(R.id.btnCloseDialog);
            btnQuit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Payment.this.finish();
                }
            });
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void customAndLoadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false);
        binding.rvOrder.setLayoutManager(layoutManager);
//        binding.rvCart.setHasFixedSize(true);

        orders = new ArrayList<>();
        orders.add(new Order(101, R.drawable.photo_ex, "The Album - BlackPink", "Artist", "Hồng", 400000, 3));
        orders.add(new Order(102, R.drawable.photo_ex, "The Album - BlackPink", "Artist", "Hồng", 400000, 1));
        orders.add(new Order(103, R.drawable.photo_ex, "The Album - BlackPink", "Artist", "Hồng", 400000, 1));


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
    private void addEvents(){
        binding.btnChangeVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, ShowVoucher.class);
                startActivity(intent);
            }
        });
        binding.btnViewMoreAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, PickAddress.class);
                startActivity(intent);
            }
        });
    }
    }
