package com.group2.pop4u_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.color.MaterialColors;
import com.group2.api.DAO.ProductDAO;
import com.group2.api.DAO.ProductResponseDAO;
import com.group2.api.Services.ProductService;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.SearchScreen.SearchDashboardFragment;
import com.group2.pop4u_app.databinding.ActivityMainBinding;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

//    BottomNavigationMenuView bottomNavigationMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.topAppBar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_main_action_bar, menu);
        binding.topAppBar.setTitle(R.string.home_title);
//        binding.appBarLayout.setStatusBarForegroundColor(getResources().color.md_theme_surfaceContainerLow);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnFilterProduct) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}