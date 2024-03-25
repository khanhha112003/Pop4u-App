package com.group2.pop4u_app.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.group2.pop4u_app.R;

public class OnBoarding1 extends AppCompatActivity {

    ViewPager mSliceViewpager;
    LinearLayout mDotLayout;
    Button skipbtn,backtn,nextbtn;
    TextView[] dots;
    BoardPagerAdapter boardPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding1);

        skipbtn = findViewById(R.id.btnSkip);
        nextbtn = findViewById(R.id.btnNext);
        backtn = findViewById(R.id.btnBack);

        backtn.setOnClickListener(view -> {

            if (getitem(0) > 0)
                mSliceViewpager.setCurrentItem(getitem(-1), true);
        });
        nextbtn.setOnClickListener(view -> {

            if (getitem(0) < 3)
                mSliceViewpager.setCurrentItem(getitem(1), true);
            else {
                Intent intent = new Intent(OnBoarding1.this, SplashScr.class );
                startActivity(intent);
                finish();
            }
        });
        skipbtn.setOnClickListener(view -> {

                Intent intent = new Intent(OnBoarding1.this, SplashScr.class );
                startActivity(intent);
                finish();
        });



        mSliceViewpager = (ViewPager) findViewById(R.id.sliceViewpager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        boardPagerAdapter = new BoardPagerAdapter(this);

        mSliceViewpager.setAdapter(boardPagerAdapter);

        setUpIndicator(0);
        mSliceViewpager.addOnPageChangeListener(viewListener);

    }

    public void  setUpIndicator(int i){
        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for ( i = 0 ;i< dots.length;i ++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.md_theme_secondaryFixed_mediumContrast,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        dots[i].setTextColor(getResources().getColor(R.color.md_theme_primary_highContrast,getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpIndicator(position);
            if (position >0){

                backtn.setVisibility(View.VISIBLE);

            }else {
                backtn.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){
        return  mSliceViewpager.getCurrentItem() + i;

    }

}