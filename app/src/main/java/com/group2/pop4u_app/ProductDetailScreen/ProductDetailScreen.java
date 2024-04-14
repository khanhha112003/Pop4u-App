package com.group2.pop4u_app.ProductDetailScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.group2.adapter.MiniProductCardRecyclerAdapter;
import com.group2.adapter.ProductImgAdapter;
import com.group2.api.Services.ProductService;
import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityProductDetailScreenBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProductDetailScreen extends AppCompatActivity {

    ActivityProductDetailScreenBinding binding;
    private ProductImgAdapter adapter;
    MiniProductCardRecyclerAdapter artistProductAdapter;
    ArrayList<Product> productArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProductDetailScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bindingBackButton();
        ViewPager viewPagerProductImages = findViewById(R.id.imvProductImage);
        adapter = new ProductImgAdapter(this);
        viewPagerProductImages.setAdapter(adapter);
        updateIndicator(0, adapter.getCount());

        artistProductAdapter = new MiniProductCardRecyclerAdapter(this, productArrayList);
        binding.rccProductRelevant.setAdapter(artistProductAdapter);

        viewPagerProductImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed
            }

            @Override
            public void onPageSelected(int position) {
                // Update indicator text
                updateIndicator(position, adapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Not needed
            }
        });
        loadData();
    }

    private void updateIndicator(int position, int total) {
        String indicatorText = (position + 1) + "/" + total;
        binding.txtIndicator.setText(indicatorText);
    }

    private void bindingBackButton() {
        binding.imvProductDetailBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void loadData() {
        // Load data from server
        String productCode = getIntent().getStringExtra("productCode");
        String artistCode = getIntent().getStringExtra("artistCode");
        CompletableFuture<Product> future = ProductService.instance.getProduct(productCode);
        future.thenAccept(product -> {
            // Update UI with product data
            if (product.getProductComparingPrice() != 0) {
                binding.txtProductPrice.setText(String.format("%s đ", product.getProductComparingPrice()));
                binding.txtComparingPrice.setText(String.format("%s đ", product.getProductPrice()));
            } else {
                binding.txtProductPrice.setText(String.format("%s đ", product.getProductPrice()));
                binding.txtComparingPrice.setVisibility(View.GONE);
            }
            binding.txtProductName.setText(product.getProductName());
            binding.txtProductArtist.setText(product.getProductArtistName());
            binding.txtProductDescription.setText(product.getProductDescription());
            binding.txtProductRate.setText(String.format("%s", product.getProductRating()));
            binding.txtProductSoldAmount.setText(String.format("%s", product.getProductSoldAmount()));
            adapter.setImagesUrl(product.getListProductPhoto());
            adapter.notifyDataSetChanged();
        });

        CompletableFuture<ArrayList<Product>> futureRelated = ProductService.instance.getListProduct(null, "related", "asc", 1000, 0, artistCode);
        futureRelated.thenAccept(productsResponse -> {
            productArrayList.clear();
            productArrayList.addAll(productsResponse);
            runOnUiThread(() -> artistProductAdapter.notifyDataSetChanged());
        });

        try {
            future.get();
        } catch (Exception e) {
            Log.d("ProductListCategory", e.getMessage());
        }

        try {
            future.get();
            futureRelated.get();
        } catch (Exception e) {
            Log.d("ProductDetailScreen", "Error loading product data", e);
        }
    }
}