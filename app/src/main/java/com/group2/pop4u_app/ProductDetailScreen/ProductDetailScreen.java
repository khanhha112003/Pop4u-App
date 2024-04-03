package com.group2.pop4u_app.ProductDetailScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.adapter.ProductImgAdapter;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityProductDetailScreenBinding;

import java.util.ArrayList;
import java.util.List;
public class ProductDetailScreen extends AppCompatActivity {

    ActivityProductDetailScreenBinding binding;
    private ViewPager viewPagerProductImages;
    private ProductImgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProductDetailScreenBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbrProductDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        setUpProductImage();
        addEvents();
    }

    private void addEvents() {
        binding.crdArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailScreen.this, ArtistInfoScreen.class);
                intent.putExtra("artistID", "LDR");
                startActivity(intent);
            }
        });
    }

    private void setUpProductImage() {
                viewPagerProductImages = findViewById(R.id.imvProductImage);
        adapter = new ProductImgAdapter(this, getProductImages());
        viewPagerProductImages.setAdapter(adapter);

        updateIndicator(0, adapter.getCount());

        viewPagerProductImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Update indicator text
                updateIndicator(position, adapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.mnFilterProduct) {
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Integer> getProductImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.img);
        images.add(R.drawable.img_1);
        images.add(R.drawable.img_2);
        return images;
    }

    private void updateIndicator(int position, int total) {
        String indicatorText = (position + 1) + "/" + total;
        binding.txtIndicator.setText(indicatorText);
    }
}