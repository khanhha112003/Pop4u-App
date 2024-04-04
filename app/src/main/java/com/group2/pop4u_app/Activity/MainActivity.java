package com.group2.pop4u_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.group2.api.ProductService;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.SearchScreen.SearchDashboardFragment;
import com.group2.pop4u_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

//    BottomNavigationMenuView bottomNavigationMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductService.getListProduct();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomepageFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.ic_home) {
                replaceFragment(new HomepageFragment());
            } else if (item.getItemId() == R.id.ic_search) {
                replaceFragment(new SearchDashboardFragment());
            } else if (item.getItemId() == R.id.ic_cart) {
                replaceFragment(new CartFragment());
            } else if (item.getItemId() == R.id.ic_account) {
                replaceFragment(new AccountFragment());
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}