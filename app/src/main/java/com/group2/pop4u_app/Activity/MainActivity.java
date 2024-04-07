package com.group2.pop4u_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

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
        CompletableFuture<ProductResponseDAO> getListProductFuture = ProductService.instance.getListProduct(1, "all", "asc", 10, 0, "");
        CompletableFuture<ProductDAO> getProduct = ProductService.instance.getProduct("ASK1001");

        // Attaching a callback to handle the result of the asynchronous call
//        getListProductFuture.thenAccept(result -> {
//            runOnUiThread(() -> {
//                if (result != null && !result.getProductList().isEmpty()) {
//                    // Process the result if it's successful
//                    for (ProductDAO product : result.getProductList()) {
//                        Log.d("Product Item", product.getProductName());
//                    }
//                } else {
//                    // Handle the case when no products are found
//                    Log.d("Product Item", "Product not found");
//                }
//            });
//        });

//        getProduct.thenAccept(result -> {
//            runOnUiThread(() -> {
//                if (result != null) {
//                    // Process the result if it's successful
//                    Log.d("Product Item", result.getProductName());
//                } else {
//                    // Handle the case when no products are found
//                    Log.d("Product Item", "Product not found");
//                }
//            });
//        });

        try {
//            getListProductFuture.get();
            getProduct.get();
        } catch (ExecutionException | InterruptedException e) {
            Log.d("Product Network Request Error", "Error: " + e);
        }
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