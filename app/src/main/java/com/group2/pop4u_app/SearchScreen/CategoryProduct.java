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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    // Search param
    String params = "";
    Integer currentPage = 1;
    Integer limit = 10;
    String productOrder = "asc";
    Integer priceStart = null;
    Integer priceEnd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchScreenCategoryProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbrCategory);
        setScreenTitle();
        setLoadmore();
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
            this.productOrder = "asc";
        });

        dialog.findViewById(R.id.chipDescendPrice).setOnClickListener(v -> {
            this.productOrder = "desc";
        });

        dialog.findViewById(R.id.chipAllProduct).setOnClickListener(v -> {
            this.params = "";
            this.relayoutActionBar("Tất cả");
        });

        dialog.findViewById(R.id.chipAlbum).setOnClickListener(v -> {
            this.params = "Album";
            this.relayoutActionBar("Album");
        });

        dialog.findViewById(R.id.chipMerch).setOnClickListener(v -> {
            this.params = "Merch";
            this.relayoutActionBar("Merch");
        });

        dialog.findViewById(R.id.chipPhotobook).setOnClickListener(v -> {
            this.params = "Photobook";
            this.relayoutActionBar("Photobook");
        });

        dialog.findViewById(R.id.chipVinyl).setOnClickListener(v -> {
            this.params = "Vinyl";
            this.relayoutActionBar("Vinyl");
        });

        dialog.findViewById(R.id.chipLightstick).setOnClickListener(v -> {
            this.params = "Lightstick";
            this.relayoutActionBar("Lightstick");
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
            priceStart = null;
            priceEnd = 500000;
        });

        dialog.findViewById(R.id.chipUnder1K).setOnClickListener(v -> {
            Log.d("Filter", "500 to 1000");
            priceStart = 500000;
            priceEnd = 1000000;
        });

        dialog.findViewById(R.id.chipUnder1_5K).setOnClickListener(v -> {
            Log.d("Filter", "1000 to 2000");
            priceStart = 1000000;
            priceEnd = 1500000;
        });

        dialog.findViewById(R.id.chipUnder2K).setOnClickListener(v -> {
            Log.d("Filter", "1000 to 2000");
            priceStart = 1500000;
            priceEnd = 2000000;
        });

        dialog.findViewById(R.id.chipOver2K).setOnClickListener(v -> {
            Log.d("Filter", "Over 2000");
            priceStart = 2000000;
            priceEnd = null;
        });

        dialog.findViewById(R.id.btnApplyFilter).setOnClickListener(v -> {
            Log.d("Filter", "Apply Filter");
            currentPage = 0;
            limit = 100;
            loadData();
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btnResetFilter).setOnClickListener(v -> {
            Log.d("Filter", "Reset Filter");
            currentPage = 0;
            limit = 10;
            loadData();
            rangeSlider.setValues(2010F, 2021F);
            params = "";
            priceStart = null;
            priceEnd = null;
            relayoutActionBar("Tất cả");
            loadData();
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

    private void setScreenTitle() {
        Intent intent = getIntent();
        String recyclerID = getIntent().getStringExtra("recyclerID");
        String actionBarTitle = intent.getStringExtra("recyclerName");
        getSupportActionBar().setTitle(actionBarTitle);
        if (recyclerID.equals("album")) {
            params = "Album";
        } else if (recyclerID.equals("photobook")) {
            params = "Photobook";
        } else if (recyclerID.equals("merch")) {
            params = "Merch";
        } else if (recyclerID.equals("vinyl")) {
            params = "Vinyl";
        } else if (recyclerID.equals("lightstick")) {
            params = "Lightstick";
        } else if (recyclerID.equals("all")){
            params = null;
        }
    }


    private void loadRecycleView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        binding.rccProductCategory.setLayoutManager(gridLayoutManager);
        binding.rccProductCategory.addItemDecoration(itemDecoration);
        binding.rccProductCategory.setHasFixedSize(true);
        adapter = new BigProductCardRecyclerAdapter(this, productArrayList);
        binding.rccProductCategory.setAdapter(adapter);
    }

    private void setLoadmore() {
        binding.rccProductCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    // Load more data
                    Log.d("Loadmore", "Load more data");
                    currentPage++;
                    loadData();
                }
            }
        });
    }


    private void loadData() {
//        CompletableFuture<ArrayList<Product>> future2 = ProductService.instance.getProductByCategory(params, currentPage, null, limit, null);
        CompletableFuture<ArrayList<Product>> future = ProductService.instance.getListProduct(currentPage, "all", productOrder, limit, 0, null, priceStart, priceEnd, params);
        future.thenAccept(products -> {
            if (currentPage == 0) {
                productArrayList.clear();
            }
            productArrayList.addAll(products);
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
        try {
            future.get();
        } catch (Exception e) {
            Log.d("Category Product List", e.getMessage());
        }
    }

    private void relayoutActionBar(String title) {
        getSupportActionBar().setTitle(title);
    }
}