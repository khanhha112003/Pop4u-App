package com.group2.pop4u_app.AccountScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

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
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
                replaceFragment(new AccountPersonalInfoFragment());
            } else if (settingItem.getSettingID().equals("notification")) {
                getSupportActionBar().setTitle(settingItem.getSettingTitle());
                replaceFragment(new AccountPersonalInfoFragment());
            } else if (settingItem.getSettingID().equals("website")) {

            }
        } 
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frgtSettingContent, fragment);
        fragmentTransaction.commit();
    }
}