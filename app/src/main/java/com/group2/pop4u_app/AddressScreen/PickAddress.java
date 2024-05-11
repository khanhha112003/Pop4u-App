package com.group2.pop4u_app.AddressScreen;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;

import com.group2.adapter.AddressAdapter;
import com.group2.database_helper.LocationDatabaseHelper;
import com.group2.model.Address;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityPickAddressBinding;
import java.util.ArrayList;
import java.util.Objects;

public class PickAddress extends AppCompatActivity implements AddressAdapter.OnTapSelectAddressListener {
ActivityPickAddressBinding binding;
    AddressAdapter adapter;
    ArrayList<Address> addresses = new ArrayList<>();;
    LocationDatabaseHelper locationDatabaseHelper;
    Address choosenAddress = null;

    ActivityResultLauncher<Intent> openChooseAddressResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data == null) return;
                    Bundle args = data.getBundleExtra("data");
                    Address address = (Address) args.getSerializable("address");
                    addNewAddressData(address);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPickAddressBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrPickAddress);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        loadData();
        addEvents();
    }

    private void loadData() {
        locationDatabaseHelper = new LocationDatabaseHelper(this);
        addresses.addAll(locationDatabaseHelper.getAllAddress());
        for (Address address : addresses) {
            if (address.isDefault()) {
                choosenAddress = address;
            }
        }
        if (choosenAddress == null && !addresses.isEmpty()) {
            addresses.get(0).setDefault(true);
            locationDatabaseHelper.setDefaultAddress(addresses.get(0));
            choosenAddress = addresses.get(0);
        }

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(PickAddress.this, R.dimen.item_offset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.lvAddress.addItemDecoration(itemDecoration);
        binding.lvAddress.setLayoutManager(layoutManager);
        binding.lvAddress.setHasFixedSize(true);
        binding.lvAddress.setNestedScrollingEnabled(false);

        adapter = new AddressAdapter(PickAddress.this, R.layout.activity_item_address, addresses);
        adapter.onTapSelectAddressListener = this;
        binding.lvAddress.setAdapter(adapter);
    }

    private void addEvents() {
        binding.btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(PickAddress.this, AddAddress.class);
            openChooseAddressResult.launch(intent);
        });

        adapter.onTapSelectAddressListener = this;

        binding.btnConfirmAddress.setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("address", choosenAddress);
            intent.putExtra("data", bundle);
            setResult(Activity.RESULT_OK, intent);
            if (locationDatabaseHelper.setDefaultAddress(choosenAddress)) {
                finish();
            }
        });
    }

    private void addNewAddressData(Address address) {
        if (locationDatabaseHelper.insertData(address)) {
            addresses.clear();
            addresses.addAll(locationDatabaseHelper.getAllAddress());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plain_action_bar, menu);
        return true;
    }

    @Override
    public void onTapCheckAddress(Address address) {
        choosenAddress = address;
    }
}