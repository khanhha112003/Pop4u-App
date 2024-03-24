package com.group2.pop4u_app.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.group2.pop4u_app.databinding.SearchScreenActivityQuerySearchBinding;

public class SearchDashboardActivity extends AppCompatActivity {
    SearchScreenActivityQuerySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchScreenActivityQuerySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}