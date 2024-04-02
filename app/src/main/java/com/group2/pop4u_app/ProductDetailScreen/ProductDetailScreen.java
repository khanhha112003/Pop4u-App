package com.group2.pop4u_app.ProductDetailScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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
        setContentView(binding.getRoot());

        viewPagerProductImages = findViewById(R.id.imvProductImage);
        adapter = new ProductImgAdapter(this, getProductImages());
        viewPagerProductImages.setAdapter(adapter);

        updateIndicator(0, adapter.getCount());

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
    }

    private List<Integer> getProductImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.img);
        images.add(R.drawable.img_1);
        images.add(R.drawable.img_2);
        // Add more images if necessary
        return images;
    }

    private void updateIndicator(int position, int total) {
        String indicatorText = (position + 1) + "/" + total;
        binding.txtIndicator.setText(indicatorText);
    }
}