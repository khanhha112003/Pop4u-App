package com.group2.pop4u_app.LoginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.group2.api.Services.UserService;
import com.group2.local.LoginManagerTemp;
import com.group2.pop4u_app.SignUp.SignUp_1;
import com.group2.pop4u_app.databinding.ActivityLoginPageBinding;

import java.util.concurrent.CompletableFuture;

public class LoginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void performLogin(String username, String password) {
        CompletableFuture<String> loginFuture = UserService.instance.login(username, password);
        loginFuture.thenAccept(v -> {
           if (v != null) {
               if (!this.isFinishing()) {
                   LoginManagerTemp.isLogin = true;
                   LoginManagerTemp.token = v;
                   this.finish();
               } else {
                   this.finish();
               }
           } else {
               Log.d("Login screen", "Login fail");
           }
        });
        try {
            loginFuture.get();
        } catch (Exception e) {
            Log.d("Login screen", "Login fail");
        }
    }

    private void addEvents() {
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtUsername.getText().toString();
                String password = binding.edtPass.getText().toString();

                // Reset errors
                binding.edtUsername.setError(null);
                binding.edtPass.setError(null);

                boolean cancel = false;
                View focusView = null;

                // Check for a valid email address.
                if (!isValidUsername(username)) {
                    binding.edtUsername.setError("Bạn đã nhập sai email. Vui lòng nhập lại");
                    focusView = binding.edtUsername;
                    cancel = true;
                }

                // Check for a valid password.
                if (!isValidPassword(password)) {
                    binding.edtPass.setError("Mật khẩu phải chứa ít nhất 8 ký tự, bao gồm chữ hoa, số và ký tự đặc biệt");
                    if (focusView == null) {
                        focusView = binding.edtPass;
                    }
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    performLogin(username, password);
                }
            }
        });

        binding.txtCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUp_1.class);
                startActivity(intent);
                if (LoginPage.this != null && !LoginPage.this.isFinishing()) {
                    LoginPage.this.finish();
                }
            }
        });
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private boolean isValidPassword(String password) {
        // Password regex pattern
        return password.length() > 7;
    }

}