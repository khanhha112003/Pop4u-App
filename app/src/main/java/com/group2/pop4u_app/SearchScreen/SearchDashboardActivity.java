package com.group2.pop4u_app.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivitySearchDashboardBinding;

public class SearchDashboardActivity extends AppCompatActivity {
    ActivitySearchDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}