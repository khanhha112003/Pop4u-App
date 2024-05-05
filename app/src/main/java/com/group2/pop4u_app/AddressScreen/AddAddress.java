package com.group2.pop4u_app.AddressScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.group2.local.AddressHelper;
import com.group2.model.Address;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityAddAddressBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

public class AddAddress extends AppCompatActivity {
    ActivityAddAddressBinding binding;
    ArrayList<HashMap<String, String>> city = new ArrayList<>();
    ArrayList<HashMap<String, String>> district = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrAddAdress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        setAddAddressHandler();
        setCitySpinner();
        setDistrictSpinner();
    }
    private void setCitySpinner() {
        ArrayList<HashMap<String, String>> listCity = AddressHelper.instance.getListCity();
        city.addAll(listCity);
        ArrayList<String> cityDisplay = new ArrayList<>();
        for (HashMap<String, String> item : listCity) {
            cityDisplay.add(item.get("name"));
        }
        binding.spnSelectCity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityDisplay));
        if (!listCity.isEmpty()) {
            binding.spnSelectCity.setSelection(0);
        }
        binding.spnSelectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cityCode = listCity.get(i).get("code");
                ArrayList<HashMap<String, String>> listDistrict = AddressHelper.instance.getListDictrict(cityCode);
                ArrayList<String> districtDisplay = new ArrayList<>();
                for (HashMap<String, String> item : listDistrict) {
                    districtDisplay.add(item.get("name"));
                }
                district.clear();
                district.addAll(listDistrict);

                binding.spnSelectDistrict.setAdapter(new ArrayAdapter<>(AddAddress.this, android.R.layout.simple_spinner_dropdown_item, districtDisplay));
                binding.spnSelectDistrict.setSelection(0);
                binding.spnSelectWard.setAdapter(new ArrayAdapter<>(AddAddress.this, android.R.layout.simple_spinner_dropdown_item, new String[]{}));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setDistrictSpinner() {
        binding.spnSelectCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                String districtCode = district.get(i).get("code");
                ArrayList<HashMap<String, String>> listWard = AddressHelper.instance.getListWard(districtCode);
                ArrayList<String> wardDisplay = new ArrayList<>();
                for (HashMap<String, String> item : listWard) {
                    wardDisplay.add(item.get("name"));
                }
                binding.spnSelectWard.setAdapter(new ArrayAdapter<>(AddAddress.this, android.R.layout.simple_spinner_dropdown_item, wardDisplay));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setAddAddressHandler() {
        binding.btnAddAddressConfirm.setOnClickListener(v -> {
            String name = binding.edtAddAddressName.getText().toString();
            String phone = binding.edtAddAddressPhone.getText().toString();
            String address = binding.spnSelectWard.getSelectedItem().toString()
                            + binding.spnSelectDistrict.getSelectedItem().toString()
                            + binding.spnSelectCity.getSelectedItem().toString();
            Address addressData = new Address(name, phone, address);
            Bundle bundle = new Bundle();
            bundle.putSerializable("address", addressData);
            Intent intent = new Intent();
            intent.putExtra("data",bundle);
            setResult(RESULT_OK, intent);
        });
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