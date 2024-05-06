package com.group2.pop4u_app.PaymentScreen;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
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
import android.widget.Toast;

import com.group2.adapter.OrderAdapter;
import com.group2.api.DAO.ValidationResponseDAO;
import com.group2.api.Services.OrderService;
import com.group2.database_helper.LocationDatabaseHelper;
import com.group2.model.Address;
import com.group2.model.CartItem;
import com.group2.model.Order;
import com.group2.model.ResponseValidate;
import com.group2.pop4u_app.AddressScreen.AddAddress;
import com.group2.pop4u_app.AddressScreen.PickAddress;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.VoucherScreen.ShowVoucher;
import com.group2.pop4u_app.databinding.ActivityPaymentBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Payment extends AppCompatActivity {
    ActivityPaymentBinding binding;
    OrderAdapter adapter;
    ArrayList<Order> orders = new ArrayList<>() ;
    ArrayList<CartItem> listCheckedItem = new ArrayList<>() ;

    Address currentAddress;

    ActivityResultLauncher<Intent> openChooseAddressResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data == null) return;
                    Bundle args = data.getBundleExtra("data");
                    if (args == null) return;
                    Address address = (Address) args.getSerializable("address");
                    if (address == null) return;
                    choosenAddress(address);
                }
            });

    LocationDatabaseHelper locationDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrPayment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        binding.piProgress.setProgressCompat(33, true);
        customAndLoadData();
        calculatetotalPriceOrder();
        addEvents();
        getIntentData();
        initLocation();
    }
    private void initLocation() {
        locationDatabaseHelper = new LocationDatabaseHelper(this);
        if (locationDatabaseHelper.numOfRows() == 0) {
            binding.txtCustomerName.setText("Chưa có thông tin");
            binding.txtCustomerPhone.setText("");
            binding.txtCustomerAddress.setText("Vui lòng lựa chọn địa chỉ của bạn");
        } else {
            currentAddress = locationDatabaseHelper.getAllAddress().get(0);
            binding.txtCustomerName.setText(currentAddress.getCus_name());
            binding.txtCustomerPhone.setText(currentAddress.getCus_phone());
            binding.txtCustomerAddress.setText(currentAddress.getCus_address());
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("selectedItems");
        listCheckedItem = (ArrayList<CartItem>) args.getSerializable("listSelectedItem");

        for (int i = 0; i < listCheckedItem.size(); i++) {
            CartItem cartItem = listCheckedItem.get(i);
            orders.add(new Order(cartItem.getProductCode(), cartItem.getThumb(), cartItem.getName(), "", cartItem.getPrice(), cartItem.getQuantity()));
        }
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

            btnQuit.setOnClickListener(view -> Payment.this.finish());

            btnContinue.setOnClickListener(view -> dialog.dismiss());

            btnCloseDialog.setOnClickListener(view -> dialog.dismiss());

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
        binding.rvOrder.setHasFixedSize(true);
        adapter = new OrderAdapter(getApplicationContext(), orders);
        binding.rvOrder.setNestedScrollingEnabled(false);
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
        binding.btnChangeVoucher.setOnClickListener(v -> {
            Intent intent = new Intent(Payment.this, ShowVoucher.class);
            startActivity(intent);
        });
        binding.btnViewMoreAddress.setOnClickListener(v -> {
            Intent intent = new Intent(Payment.this, PickAddress.class);
            openChooseAddressResult.launch(intent);
        });
        binding.btnPlaceOrder.setOnClickListener(v -> {
            if (currentAddress == null) {
                return;
            }
            CompletableFuture<ResponseValidate> future = OrderService
                    .instance
                    .createOrder(
                            currentAddress.getCus_address(),
                            currentAddress.getCus_phone(),
                            getSelectedPaymentMethod(),
                            (String) binding.shipfee.getText(),
                            listCheckedItem
                    );
            future.thenAccept(response -> {
                if (response.getStatus() == 1) {
                    Intent intent = new Intent(Payment.this, PaymentSuccess.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Payment.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void choosenAddress(Address address) {
        binding.txtCustomerName.setText(address.getCus_name());
        binding.txtCustomerPhone.setText(address.getCus_phone());
        binding.txtCustomerAddress.setText(address.getCus_address());
    }

    private String getSelectedPaymentMethod() {
        if (binding.rdbCOD.isChecked()) {
            return "cash";
        } else if (binding.rdbMomo.isChecked()) {
            return "momo";
        } else if (binding.rdbNapas.isChecked()) {
            return "visa";
        }
        return "";
    }

}
