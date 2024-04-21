package com.group2.pop4u_app.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.group2.adapter.FavoriteProductListAdapter;
import com.group2.model.Product;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityFavoriteListBinding;

import java.util.ArrayList;

public class FavoriteListActivity extends AppCompatActivity {

    ActivityFavoriteListBinding binding;
    FavoriteProductListAdapter favoriteProductListAdapter;
    ArrayList<Product> productArrayList;
    Product selectedProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteListBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrFavorite);
        getSupportActionBar().setTitle(R.string.action_bar_favorite_action);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        binding.ctrNoFavorite.setVisibility(View.INVISIBLE);
        Drawable actionBarBackground = getResources().getDrawable(R.color.md_theme_surfaceContainerLow);
        getSupportActionBar().setBackgroundDrawable(actionBarBackground);
        loadData();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plain_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {

        if (productArrayList.size() == 0) {
            binding.ctrNoFavorite.setVisibility(View.VISIBLE);
        }
        favoriteProductListAdapter.setOnProductFavoriteListener(new FavoriteProductListAdapter.OnProductFavoriteListener() {
            @Override
            public void onFavoriteClick(int position) {
                selectedProduct = productArrayList.get(position);

            }
        });
    }

    private void loadData() {
        productArrayList = new ArrayList<>();
        initAdapter();
    }

    private void initAdapter() {
        favoriteProductListAdapter = new FavoriteProductListAdapter(FavoriteListActivity.this, productArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoriteListActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ItemOffsetVerticalRecycler itemOffsetVerticalRecycler = new ItemOffsetVerticalRecycler(FavoriteListActivity.this, R.dimen.item_offset);
        binding.rcvFavoriteProduct.setHasFixedSize(true);
        binding.rcvFavoriteProduct.addItemDecoration(itemOffsetVerticalRecycler);
        binding.rcvFavoriteProduct.setLayoutManager(linearLayoutManager);
        binding.rcvFavoriteProduct.setAdapter(favoriteProductListAdapter);
    }
}