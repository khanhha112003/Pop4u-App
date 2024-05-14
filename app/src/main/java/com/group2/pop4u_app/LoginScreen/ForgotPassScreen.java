package com.group2.pop4u_app.LoginScreen;

import android.content.Intent;
import android.os.Bundle;
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
import com.group2.pop4u_app.SignUp.SignUp_1;
import com.group2.pop4u_app.SignUp.SignUp_2;
import com.group2.pop4u_app.databinding.ActivityForgotPassScreenBinding;

import java.util.concurrent.CompletableFuture;

public class ForgotPassScreen extends AppCompatActivity {

    ActivityForgotPassScreenBinding binding;

    View focusErrorView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPassScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvent();


    }

    private void addEvent() {
        binding.edtEmail.setError(null);
        binding.btnForgotPass.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString();
            if (!isValidEmail(email)) {
                binding.edtEmail.setError("Vui lòng nhập Email");
                focusErrorView = binding.edtEmail;
                focusErrorView.requestFocus();
                return;
            }
            if (focusErrorView != null) {
                focusErrorView.clearFocus();
            }
            // Call API
            CompletableFuture<ResponseValidate> future = UserService.instance.forgotPassword(email);
            future.thenAccept(responseValidate -> {
                if (responseValidate != null) {
                    if (responseValidate.getStatus() == 1) {
                        // Show dialog
                        Toast.makeText(ForgotPassScreen.this, responseValidate.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPassScreen.this, SignUp_2.class);
                        intent.putExtra("email", email);
                        intent.putExtra("isFromForgotPass", true);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ForgotPassScreen.this, responseValidate.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPassScreen.this, "Có lỗi đã xảy ra, xin thử lại", Toast.LENGTH_SHORT).show();
                }
            });

            try {
                future.get();
            } catch (Exception e) {
                Toast.makeText(ForgotPassScreen.this, "Có lỗi đã xảy ra, xin thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}