package com.group2.pop4u_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityLoginPageBinding;

public class LoginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPass.getText().toString();

                if (!isValidEmail(email)) {
                    // If the email is invalid
                    Toast.makeText(LoginPage.this, "Bạn đã nhập sai email. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    // If the password is invalid but the email is valid
                    Toast.makeText(LoginPage.this, "Bạn đã nhập sai mật khẩu. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                } else {
                    // If both are valid
                    Toast.makeText(LoginPage.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return password.matches(".*[a-zA-Z]+.*") && password.matches(".*[0-9]+.*");
    }
}