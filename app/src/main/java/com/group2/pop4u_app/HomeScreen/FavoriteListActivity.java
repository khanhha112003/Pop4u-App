package com.group2.pop4u_app.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.group2.adapter.FavoriteProductListAdapter;
import com.group2.database_helper.FavoriteDatabaseHelper;
import com.group2.database_helper.OrderDatabaseHelper;
import com.group2.model.Product;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetVerticalRecycler;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityFavoriteListBinding;

import java.util.ArrayList;

public class FavoriteListActivity extends AppCompatActivity {

    ActivityFavoriteListBinding binding;
    FavoriteProductListAdapter favoriteProductListAdapter;
    ArrayList<Product> productArrayList;
    Product selectedProduct;
    FavoriteDatabaseHelper favoriteDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteListBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrFavorite);
        getSupportActionBar().setTitle(R.string.action_bar_favorite_action);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        binding.ctrNoFavorite.setVisibility(View.INVISIBLE);
        Drawable actionBarBackground = FavoriteListActivity.this.getResources().getDrawable(R.color.md_theme_surfaceContainerLow);
        getSupportActionBar().setBackgroundDrawable(actionBarBackground);
        loadData();
        initAdapter();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        favoriteProductListAdapter.notifyDataSetChanged();;
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

        favoriteProductListAdapter.setOnClickListener(new FavoriteProductListAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Product product) {
                Intent intent = new Intent(FavoriteListActivity.this, ProductDetailScreen.class);
                intent.putExtra("productCode", product.getProductCode());
                intent.putExtra("artistCode", product.getArtistCode());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        productArrayList = new ArrayList<>();
        favoriteDatabaseHelper = new FavoriteDatabaseHelper(FavoriteListActivity.this);
        SQLiteDatabase database = favoriteDatabaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.query(
                    FavoriteDatabaseHelper.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(0);
                    String productName = cursor.getString(1);
                    String productCode = cursor.getString(2);
                    double productPrice = cursor.getDouble(3);
                    double salePercent = cursor.getDouble(4);
                    String productImage = cursor.getString(5);
                    String artistCode = cursor.getString(6);
                    String productArtist = cursor.getString(7);
                    ArrayList<String> listProductPhoto = new ArrayList<>();
                    listProductPhoto.add(productImage);

                    Product product = new Product(productCode, productName, listProductPhoto, productArtist, artistCode, "", (int) productPrice, 0,(int) salePercent, (double) 0, 0, 0, 0, "");

                    productArrayList.add(product);

                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
    }

    private void initAdapter() {
        favoriteProductListAdapter = new FavoriteProductListAdapter(FavoriteListActivity.this, productArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoriteListActivity.this, LinearLayoutManager.VERTICAL, false);
        ItemOffsetVerticalRecycler itemOffsetVerticalRecycler = new ItemOffsetVerticalRecycler(FavoriteListActivity.this, R.dimen.item_offset);
        binding.rcvFavoriteProduct.setHasFixedSize(true);
        binding.rcvFavoriteProduct.addItemDecoration(itemOffsetVerticalRecycler);
        binding.rcvFavoriteProduct.setLayoutManager(linearLayoutManager);
        binding.rcvFavoriteProduct.setAdapter(favoriteProductListAdapter);
    }
}