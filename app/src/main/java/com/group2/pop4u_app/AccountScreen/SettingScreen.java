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
        setContentView(binding.getRoot());
        loadSettingScreen();
    }

    private void loadSettingScreen() {
        Intent intent = getIntent();
        SettingItem settingItem = (SettingItem) intent.getSerializableExtra("settingItem");
        if (settingItem != null && settingItem.getSettingID().equals("personal_info")) {
            replaceFragment(new AccountPersonalInfoFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frgtSettingContent, fragment);
        fragmentTransaction.commit();
    }
}