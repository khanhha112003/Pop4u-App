package com.group2.pop4u_app.SearchScreen;

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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.slider.RangeSlider;
import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.api.Services.ProductService;
import com.group2.model.Product;
import com.group2.pop4u_app.ItemOffsetDecoration.ItemOffsetDecoration;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.SearchScreenCategoryProductBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CategoryProduct extends AppCompatActivity {
    SearchScreenCategoryProductBinding binding;

    BigProductCardRecyclerAdapter adapter;
    ArrayList<Product> productArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchScreenCategoryProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbrCategory);
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
        Dialog dialog = new Dialog(CategoryProduct.this);
        dialog.setContentView(R.layout.dialog_filter_product);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.findViewById(R.id.chipAscendPrice).setOnClickListener(v -> {
            Log.d("Filter", "Ascend Price");
        });

        dialog.findViewById(R.id.chipDescendPrice).setOnClickListener(v -> {
            Log.d("Filter", "Descend Price");
        });

        dialog.findViewById(R.id.chipAllProduct).setOnClickListener(v -> {
            Log.d("Filter", "All Product");
        });

        dialog.findViewById(R.id.chipAlbum).setOnClickListener(v -> {
            Log.d("Filter", "Album");
        });

        dialog.findViewById(R.id.chipMerch).setOnClickListener(v -> {
            Log.d("Filter", "Merch");
        });

        dialog.findViewById(R.id.chipPhotobook).setOnClickListener(v -> {
            Log.d("Filter", "Photobook");
        });

        dialog.findViewById(R.id.chipVinyl).setOnClickListener(v -> {
            Log.d("Filter", "Vinyl");
        });

        dialog.findViewById(R.id.chipLightstick).setOnClickListener(v -> {
            Log.d("Filter", "Lightstick");
        });

        RangeSlider rangeSlider = dialog.findViewById(R.id.sliderYearRangeProductRelease);
        rangeSlider.addOnChangeListener((slider, value, fromUser) -> {
            String upperValue = String.valueOf(slider.getValues().get(1).intValue());
            String lowerValue = String.valueOf(slider.getValues().get(0).intValue());
            String initRangeValue = String.format("Year Range: %s - %s", lowerValue, upperValue);
            TextView rangeValue = dialog.findViewById(R.id.txtRangeDateValue);
            rangeValue.setText(initRangeValue);
        });

        TextView rangeValue = dialog.findViewById(R.id.txtRangeDateValue);
        rangeValue.setText(String.format("Year Range: %s - %s",
                String.valueOf(rangeSlider.getValues().get(0).intValue()),
                String.valueOf(rangeSlider.getValues().get(1).intValue())));

        dialog.findViewById(R.id.chipUnder500).setOnClickListener(v -> {
            Log.d("Filter", "Under 500");
        });

        dialog.findViewById(R.id.chipUnder1K).setOnClickListener(v -> {
            Log.d("Filter", "500 to 1000");
        });

        dialog.findViewById(R.id.chipUnder1_5K).setOnClickListener(v -> {
            Log.d("Filter", "1000 to 2000");
        });

        dialog.findViewById(R.id.chipUnder2K).setOnClickListener(v -> {
            Log.d("Filter", "1000 to 2000");
        });

        dialog.findViewById(R.id.chipOver2K).setOnClickListener(v -> {
            Log.d("Filter", "Over 2000");
        });

        dialog.findViewById(R.id.btnApplyFilter).setOnClickListener(v -> {
            Log.d("Filter", "Apply Filter");
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btnResetFilter).setOnClickListener(v -> {
            Log.d("Filter", "Reset Filter");
            rangeSlider.setValues(2010F, 2021F);
            rangeValue.setText(String.format("Year Range: %s - %s",
                    String.valueOf(rangeSlider.getValues().get(0).intValue()),
                    String.valueOf(rangeSlider.getValues().get(1).intValue())));
        });

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
        binding.rccProductCategory.setLayoutManager(gridLayoutManager);
        binding.rccProductCategory.addItemDecoration(itemDecoration);
        binding.rccProductCategory.setHasFixedSize(true);
        adapter = new BigProductCardRecyclerAdapter(this, productArrayList);
        binding.rccProductCategory.setAdapter(adapter);
    }


    private void loadData() {
        String extra = getIntent().getStringExtra("recyclerID");
        if (extra != null) {
            String params = "";
            if (extra.equals("album")) {
                params = "Album";
            } else if (extra.equals("photobook")) {
                params = "Photobook";
            } else if (extra.equals("merch")) {
                params = "Merch";
            } else if (extra.equals("vinyl")) {
                params = "Vinyl";
            } else if (extra.equals("lightstick")) {
                params = "Lightstick";
            } else if (extra.equals("all")){
                params = "Tất cả sản phẩm";
            }
            CompletableFuture<ArrayList<Product>> future = ProductService.instance.getListProduct(null, params, "asc", 1000, 0, null);
            future.thenAccept(products -> {
                productArrayList.clear();
                productArrayList.addAll(products);
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            });
            try {
                future.get();
            } catch (Exception e) {
                Log.d("Category Product List", e.getMessage());
            }
        }
    }
}