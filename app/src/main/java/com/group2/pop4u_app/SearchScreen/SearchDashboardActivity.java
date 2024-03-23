package com.group2.pop4u_app.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.SearchScreenActivitySearchDashboardBinding;

public class SearchDashboardActivity extends AppCompatActivity {
    SearchScreenActivitySearchDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchScreenActivitySearchDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
    }

    private void setToolbar() {
        binding.tbToolbarTitle.setTitleTextAppearance(this, R.style.SearchScreenToolbarTitle);
    }
}