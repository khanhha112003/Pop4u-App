package com.group2.pop4u_app.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.api.Services.ProductService;
import com.group2.model.Product;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityProductListCategoryBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ProductListCategory extends AppCompatActivity {

    ActivityProductListCategoryBinding binding;

    BigProductCardRecyclerAdapter bigProductCardRecyclerAdapter;
    ArrayList<Product> productArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbrProductList);
        loadRecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_list_screen, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.mnFilterProduct) {
            openFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFilterDialog() {
        Dialog dialog = new Dialog(ProductListCategory.this);
        dialog.setContentView(R.layout.dialog_filter_product);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            layoutParams.horizontalMargin = 0;
            layoutParams.verticalMargin = 0;
            window.setAttributes(layoutParams);
        }

        dialog.show();
    }


    private void loadRecycleView() {
        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("recyclerName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        binding.rccProductListInCategory.setLayoutManager(gridLayoutManager);
        binding.rccProductListInCategory.addItemDecoration(itemDecoration);
        binding.rccProductListInCategory.setHasFixedSize(true);
        bigProductCardRecyclerAdapter = new BigProductCardRecyclerAdapter(this, productArrayList);
        binding.rccProductListInCategory.setAdapter(bigProductCardRecyclerAdapter);
    }

    private void loadData() {
        String extra = getIntent().getStringExtra("recyclerID");
        if (extra != null) {
            String params = "";
            if (extra.equals("saleProduct")) {
                params = "sale";
            } else if (extra.equals("newProduct")) {
                params = "new";
            }
            CompletableFuture<ArrayList<Product>> future = ProductService.instance.getListProduct(null, params, "asc", 1000, 0, null);
            future.thenAccept(products -> {
                productArrayList.clear();
                productArrayList.addAll(products);
                runOnUiThread(() -> bigProductCardRecyclerAdapter.notifyDataSetChanged());
            });
            try {
                future.get();
            } catch (Exception e) {
                Log.d("ProductListCategory", e.getMessage());
            }
        }
    }
}