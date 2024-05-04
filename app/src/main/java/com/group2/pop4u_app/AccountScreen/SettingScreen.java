package com.group2.pop4u_app.AccountScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.group2.model.SettingItem;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivitySettingScreenBinding;

public class SettingScreen extends AppCompatActivity {

    ActivitySettingScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingScreenBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        loadSettingScreen();
    }

    private void loadSettingScreen() {
        Intent intent = getIntent();
        SettingItem settingItem = (SettingItem) intent.getSerializableExtra("settingItem");
        if (settingItem != null) {
            if (settingItem.getSettingID().equals("personal_info")) {
                replaceFragment(new AccountPersonalInfoFragment());
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
            } else if (settingItem.getSettingID().equals("notification")) {
                replaceFragment(new NotificationSettingFragment());
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
            } else if (settingItem.getSettingID().equals("payment_methods")) {
                replaceFragment(new PaymentMethodFragment());
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
            } else if (settingItem.getSettingID().equals("website")) {
                replaceFragment(new WebsiteFragment());
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
            } else if (settingItem.getSettingID().equals("termsAndPolicy")) {
                replaceFragment(new TermsFragment());
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
            } else if (settingItem.getSettingID().equals("helpAndReport")) {
                replaceFragment(new HelpsNReportFragment());
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
            }else if (settingItem.getSettingID().equals("address_list")) {
                replaceFragment(new AddressFragment());
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
            }
        } 
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frgtSettingContent, fragment);
        fragmentTransaction.commit();
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