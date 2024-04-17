package com.group2.pop4u_app.LoginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.group2.api.Services.UserService;
import com.group2.local.LoginManagerTemp;
import com.group2.pop4u_app.Activity.MainActivity;
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
        CompletableFuture<Boolean> loginFuture = UserService.instance.login(username, password);
        loginFuture.thenAccept(v -> {
           if (v) {
               if (!this.isFinishing()) {
                   LoginManagerTemp.isLogin = true;
                   this.finish();
               }
           } else {
               Log.d("Login screen", "Login fail");
           }
        });
    }

    private void addEvents() {
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPass.getText().toString();

                // Reset errors
                binding.edtEmail.setError(null);
                binding.edtPass.setError(null);

                boolean cancel = false;
                View focusView = null;

                // Check for a valid email address.
                if (!isValidEmail(email)) {
                    binding.edtEmail.setError("Bạn đã nhập sai email. Vui lòng nhập lại");
                    focusView = binding.edtEmail;
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
                    // Navigate to HomePage
                    Intent intent = new Intent(LoginPage.this, MainActivity.class); // Adjust this to your HomePage activity
                    startActivity(intent);
                    finish(); // Optional: If you don't want users to return to the login screen when pressing back
                }
            }
        });

        binding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUp_1.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Password regex pattern
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordPattern);
    }

}