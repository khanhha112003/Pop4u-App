package com.group2.pop4u_app.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


import com.group2.pop4u_app.R;

public class SplashScr extends AppCompatActivity {

    // Thời gian đợi trước khi chuyển hướng (miliseconds)
    private static final int SPLASH_TIME_OUT = 1000; // 1 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scr);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển hướng tới màn hình Onboarding
                Intent i = new Intent(SplashScr.this, OnBoarding.class);
                startActivity(i);

                // Đóng màn hình splash
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
