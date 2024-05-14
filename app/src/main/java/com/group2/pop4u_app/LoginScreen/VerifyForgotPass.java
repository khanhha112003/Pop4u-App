package com.group2.pop4u_app.LoginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.api.Services.UserService;
import com.group2.model.ResponseValidate;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.SignUp.SignUp_2;
import com.group2.pop4u_app.SignUp.SignUp_3;
import com.group2.pop4u_app.databinding.ActivityVerifyForgotPassBinding;

import java.util.concurrent.CompletableFuture;

public class VerifyForgotPass extends AppCompatActivity {

    ActivityVerifyForgotPassBinding binding;
    String email = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityVerifyForgotPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConfirm.setOnClickListener(v -> otpVerification());
    }

    @Override
    protected void onStart() {
        super.onStart();
        email = getIntent().getStringExtra("email");
    }

    private void otpVerification() {
        String otp = String.valueOf(binding.edtCode.getText());
        if (otp.length() != 6 || !otp.matches("[0-9]+")) {
            Toast.makeText(VerifyForgotPass.this, "Mã OTP không hợp lệ", Toast.LENGTH_SHORT).show();
        } else {
            CompletableFuture<ResponseValidate> future = UserService.instance.validate_otp(email, otp);
            future.thenAccept(v -> {
                if (v.getStatus() == 1) {
                    Boolean isFromForgotPass = getIntent().getBooleanExtra("isFromForgotPass", false);
                    if (isFromForgotPass) {
                        Toast.makeText(VerifyForgotPass.this, "Xác thưc thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VerifyForgotPass.this, CreateNewPass.class);
                        intent.putExtra("username", email);
                        intent.putExtra("otp", otp);
                        this.finish();
                        startActivity(intent);
                    } else {
                        // TODO: Them man hinh chinh password o day
                        Toast.makeText(VerifyForgotPass.this, "Xác thưc thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VerifyForgotPass.this, CreateNewPass.class);
                        intent.putExtra("email", email);
                        intent.putExtra("otp", otp);
                        this.finish();
                        startActivity(intent);
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(VerifyForgotPass.this, v.getMessage(), Toast.LENGTH_SHORT).show();
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