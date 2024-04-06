package com.group2.pop4u_app.SplashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.group2.pop4u_app.Activity.MainActivity;
import com.group2.pop4u_app.R;

public class OnBoarding extends AppCompatActivity {

    ViewPager mSliceViewpager;
    LinearLayout mDotLayout;
    Button skipbtn, startBtn;
    TextView[] dots;
    BoardPagerAdapter boardPagerAdapter;
    Animation startanim;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        skipbtn = findViewById(R.id.btnSkip);
        startBtn = findViewById(R.id.btnStart);
        startanim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.startbtn_anim);

        startBtn.setVisibility(View.INVISIBLE);

        skipbtn.setOnClickListener(view -> startNextActivity(MainActivity.class));

        startBtn.setOnClickListener(view -> {
            startBtn.setBackgroundColor(R.color.md_theme_onSecondaryContainer_highContrast); // Change color to red (you can use any color)
            startNextActivity(MainActivity.class);
        });

        mSliceViewpager = findViewById(R.id.sliceViewpager);
        mDotLayout = findViewById(R.id.indicator_layout);

        boardPagerAdapter = new BoardPagerAdapter(this);
        mSliceViewpager.setAdapter(boardPagerAdapter);

        mSliceViewpager.addOnPageChangeListener(viewListener);
        setUpIndicator(0);
    }

    private void startNextActivity(Class<?> cls) {
        Intent intent = new Intent(OnBoarding.this, cls);
        startActivity(intent);
        finish();
    }

    public void setUpIndicator(int position) {
        dots = new TextView[boardPagerAdapter.getCount()];
        mDotLayout.removeAllViews();

        for (int i = 0; i < boardPagerAdapter.getCount(); i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.md_theme_inversePrimary_mediumContrast, getTheme()));
            mDotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.md_theme_onTertiaryFixedVariant, getTheme()));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);
            startBtn.setVisibility(position == boardPagerAdapter.getCount() - 1 ? View.VISIBLE : View.INVISIBLE);
            startBtn.setAnimation(startanim);
            skipbtn.setVisibility(position == boardPagerAdapter.getCount() - 1 ? View.INVISIBLE : View.VISIBLE);
            mDotLayout.setVisibility(position == boardPagerAdapter.getCount() - 1 ? View.INVISIBLE : View.VISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
