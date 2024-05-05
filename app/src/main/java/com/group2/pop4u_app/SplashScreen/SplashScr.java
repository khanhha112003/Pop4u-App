package com.group2.pop4u_app.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.group2.api.Services.UserService;
import com.group2.database_helper.LoginDatabaseHelper;
import com.group2.local.LoginManagerTemp;
import com.group2.model.User;
import com.group2.pop4u_app.MainActivity;
import com.group2.pop4u_app.R;

import java.util.concurrent.CompletableFuture;

public class SplashScr extends AppCompatActivity {

    // Thời gian đợi trước khi chuyển hướng (miliseconds)
    private static final int SPLASH_TIME_OUT = 1000; // 1 giây

    LoginDatabaseHelper loginDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scr);

        loginDatabaseHelper = new LoginDatabaseHelper(this);

        Boolean syncValue = loginDatabaseHelper.syncToken();
        CompletableFuture<Boolean> future = UserService.instance.validateToken();
        future.thenAccept(result -> {
            if (result) {
                Intent i = new Intent(SplashScr.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                loginDatabaseHelper.clearAllData();
                LoginManagerTemp.token = "";
                LoginManagerTemp.isLogin = false;
                this.goToOnBoarding();
            }
        });
        try {
            future.get();
        } catch (Exception e) {
            Log.d("SplashScr", e.getMessage());
        }
    }

    private void goToOnBoarding() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScr.this, OnBoarding.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
