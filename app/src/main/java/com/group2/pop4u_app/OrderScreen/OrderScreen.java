package com.group2.pop4u_app.OrderScreen;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.group2.pop4u_app.R;

public class OrderScreen extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        viewPager = findViewById(R.id.vpOrder);
        tabLayout = findViewById(R.id.tlOrder);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        viewPagerAdapter.addFragment(new OrderedFragment(), "Đã đặt");
        viewPagerAdapter.addFragment(new ProcessingFragment(), "Đang xử lý");
        viewPagerAdapter.addFragment(new SuccessfulOrderFragment(), "Thành công");
        viewPagerAdapter.addFragment(new CanceledOrderFragment(), "Đã hủy");

        viewPager.setAdapter(viewPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getFragmentTitle().get(position))
        ).attach();

    }
}