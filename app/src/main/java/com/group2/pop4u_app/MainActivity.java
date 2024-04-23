package com.group2.pop4u_app;
import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.snackbar.Snackbar;
import com.group2.database_helper.OrderDatabaseHelper;
import com.group2.local.LoginManagerTemp;
import com.group2.pop4u_app.AccountScreen.AccountFragment;
import com.group2.pop4u_app.CartScreen.CartFragment;
import com.group2.pop4u_app.HomeScreen.HomepageFragment;
import com.group2.pop4u_app.LoginScreen.LoginPage;
import com.group2.pop4u_app.HomeScreen.FavoriteListActivity;
import com.group2.pop4u_app.OrderScreen.OrderScreen;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.SearchScreen.SearchDashboardFragment;
import com.group2.pop4u_app.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private int savedLoginItemIndex = -1;
    private boolean navigateToAnotherActivity;

    OrderDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.savedLoginItemIndex = R.id.ic_home;
        this.navigateToAnotherActivity = false;
        setContentView(binding.getRoot());
        setSupportActionBar(binding.topAppBar);
        replaceFragment(new HomepageFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            this.navigateToAnotherActivity = false;
            if (item.getItemId() == R.id.ic_home) {
                this.savedLoginItemIndex = R.id.ic_home;
                replaceFragment(new HomepageFragment());
                getSupportActionBar().setTitle(R.string.home_title);
            } else if (item.getItemId() == R.id.ic_search) {
                this.savedLoginItemIndex = R.id.ic_search;
                replaceFragment(new SearchDashboardFragment());
                getSupportActionBar().setTitle(R.string.search_title);
            } else if (item.getItemId() == R.id.ic_cart) {
                if (LoginManagerTemp.isLogin) {
                    this.savedLoginItemIndex = R.id.ic_cart;
                    replaceFragment(new CartFragment());
                    getSupportActionBar().setTitle(R.string.cart_title);
                } else {
                    this.navigateToAnotherActivity = true;
                    openLogin();
                }
            } else if (item.getItemId() == R.id.ic_account) {
                if (LoginManagerTemp.isLogin) {
                    this.savedLoginItemIndex = R.id.ic_account;
                    replaceFragment(new AccountFragment());
                    getSupportActionBar().setTitle(R.string.account_title);
                } else {
                    this.navigateToAnotherActivity = true;
                    openLogin();
                }
            }
            return true;
        });

        databaseHelper = new OrderDatabaseHelper(MainActivity.this);
        setCartBadge();
    }

    private void setCartBadge() {
        BadgeDrawable badge = binding.bottomNavigationView.getOrCreateBadge(R.id.ic_cart);
        badge.setVisible(true);
        badge.setNumber(databaseHelper.numOfRows());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginManagerTemp.isJustFinishRegisterSuccess) {
            LoginManagerTemp.isJustFinishRegisterSuccess = false;
            this.savedLoginItemIndex = R.id.ic_account;
            View view = binding.bottomNavigationView.findViewById(this.savedLoginItemIndex);
            view.performClick();
        } else if (this.savedLoginItemIndex != -1 && navigateToAnotherActivity) {
            View view = binding.bottomNavigationView.findViewById(this.savedLoginItemIndex);
            view.performClick();
        }
        setCartBadge();
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
        return true;
    }

    public void openLogin() {
        Toast.makeText(this, R.string.request_to_sign_in, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnOpenFavoriteProducts) {
            Intent intent = new Intent(MainActivity.this, FavoriteListActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.mnOpenOrders) {
            Intent intent = new Intent(MainActivity.this, OrderScreen.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void backHome() {
        this.savedLoginItemIndex = R.id.ic_home;
        View view = binding.bottomNavigationView.findViewById(this.savedLoginItemIndex);
        view.performClick();
    }

    public void fromSplashToLogin() {
        this.savedLoginItemIndex = R.id.ic_home;
        this.navigateToAnotherActivity = true;
        openLogin();
    }
}