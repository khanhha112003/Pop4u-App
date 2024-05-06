package com.group2.pop4u_app.AddressScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.group2.local.AddressHelper;
import com.group2.model.Address;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityAddAddressBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class AddAddress extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AddressHelper.AddressHelperGetData {
    ActivityAddAddressBinding binding;
    ArrayList<HashMap<String, String>> city = new ArrayList<>();
    ArrayList<HashMap<String, String>> district = new ArrayList<>();

    Spinner spnSelectCity, spnSelectDistrict, spnSelectWard;

    String[] cityDisplay, districtDisplay, wardDisplay;

    ArrayAdapter<CharSequence> cityAdapter, districtAdapter, wardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrAddAdress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AddressHelper.instance.setAddressHelperListener(this);
        setContentView(binding.getRoot());
        setAddAddressHandler();
        setCitySpinner();
        setDistrictSpinner();
    }
    private void setCitySpinner() {
        spnSelectCity = binding.spnSelectCity;
        ArrayList<HashMap<String, String>> listCity = AddressHelper.instance.getListCity();
        city.addAll(listCity);
        cityDisplay = new String[listCity.size()];
        for (HashMap<String, String> item : listCity) {
            cityDisplay[city.indexOf(item)] = item.get("name");
        }
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityDisplay);
        spnSelectCity.setAdapter(cityAdapter);
        spnSelectCity.setOnItemSelectedListener(this);
        spnSelectCity.setSelection(0);
    }

    private void setDistrictSpinner() {
        spnSelectDistrict = binding.spnSelectDistrict;
        districtDisplay = new String[]{""};
        districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districtDisplay);
        spnSelectDistrict.setAdapter(districtAdapter);
        spnSelectDistrict.setOnItemSelectedListener(this);
        spnSelectDistrict.setSelection(0);
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
            finish();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("AddAddress", "onItemSelected: " + position);
        if (parent.getId() == R.id.spnSelectCity) {
            onCitySelected(position);
        } else if (parent.getId() == R.id.spnSelectDistrict) {
            onDistrictSelected(position);
        }
    }

    private void onCitySelected(Integer position) {
        if (city.isEmpty()) {
            return;
        }
        String cityCode = city.get(position).get("code");
        spnSelectCity.setSelection(position);
        AddressHelper.instance.getListDictrict(cityCode);
    }

    private void onDistrictSelected(Integer position) {
        if (district.isEmpty()) {
            return;
        }
        String districtCode = district.get(position).get("code");
        spnSelectDistrict.setSelection(position);
        AddressHelper.instance.getListWard(districtCode);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onGetDataDistrict(ArrayList<HashMap<String, String>> data) {
        districtDisplay = new String[data.size()];
        for (HashMap<String, String> item : data) {
            districtDisplay[data.indexOf(item)] = item.get("name");
        }
        district.clear();
        district.addAll(data);
        districtAdapter = new ArrayAdapter<>(AddAddress.this, android.R.layout.simple_spinner_dropdown_item, districtDisplay);
        runOnUiThread(() -> {
                    spnSelectDistrict.setAdapter(districtAdapter);
                    spnSelectDistrict.setSelection(0);
                    binding.spnSelectWard.setAdapter(new ArrayAdapter<>(AddAddress.this, android.R.layout.simple_spinner_dropdown_item, new String[]{""}));
                    binding.spnSelectWard.setSelection(0);
                }
        );
    }

    @Override
    public void onGetDataWard(ArrayList<HashMap<String, String>> data) {
        wardDisplay = new String[data.size()];
        for (HashMap<String, String> item : data) {
            wardDisplay[data.indexOf(item)] = item.get("name");
        }
        runOnUiThread(() -> {
                    binding.spnSelectWard.setAdapter(new ArrayAdapter<>(AddAddress.this, android.R.layout.simple_spinner_dropdown_item, wardDisplay));
                    binding.spnSelectWard.setSelection(0);
                }
        );
    }
}