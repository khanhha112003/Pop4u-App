package com.group2.pop4u_app.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivitySignUp1Binding;

public class SignUp_1 extends AppCompatActivity {

    ActivitySignUp1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUp1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPass.getText().toString();
                String confirmPassword = binding.edtConfirmPass.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(SignUp_1.this, "Email không hợp lệ. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(SignUp_1.this, "Mật khẩu phải chứa ít nhất 8 ký tự bao gồm chữ hoa, số và ký tự đặc biệt.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUp_1.this, "Mật khẩu nhập lại không khớp. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SignUp_1.this, SignUp_2.class);
                    startActivity(intent);

                }
            }
        });

    }

    private boolean isValidEmail(String email) {
        // Basic pattern check for email validation
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Check if the password contains at least 8 characters, includes at least one uppercase letter, one number, and one special character
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordPattern);
    }
}