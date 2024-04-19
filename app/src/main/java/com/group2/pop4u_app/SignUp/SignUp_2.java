package com.group2.pop4u_app.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.api.Services.UserService;
import com.group2.model.ResponseValidate;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivitySignUp2Binding;

import java.util.concurrent.CompletableFuture;

public class SignUp_2 extends AppCompatActivity {

    ActivitySignUp2Binding binding;

    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpVerification();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        email = getIntent().getStringExtra("email");
    }

    private void otpVerification() {
        String otp = String.valueOf(binding.edtCode.getText());
        if (otp.length() != 8) {

        } else {
            CompletableFuture<ResponseValidate> future = UserService.instance.validate_otp(email, otp);
            future.thenAccept(v -> {
                if (v.getStatus() == 1) {
                    Toast.makeText(SignUp_2.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp_2.this, SignUp_3.class);
                    intent.putExtra("username", email);
                    startActivity(intent);
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(SignUp_2.this, v.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
            try {
                future.get();
            } catch (Exception e) {
                Log.d("ProductListCategory", e.getMessage());
            }
        }
    }
}