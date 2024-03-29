package com.group2.pop4u_app.AccountScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivitySettingScreenBinding;

public class SettingScreen extends AppCompatActivity {

    ActivitySettingScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}