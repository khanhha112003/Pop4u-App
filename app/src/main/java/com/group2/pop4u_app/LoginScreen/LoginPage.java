package com.group2.pop4u_app.LoginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.SignUp.SignUp_1;
import com.group2.pop4u_app.databinding.ActivityLoginPageBinding;
import com.group2.pop4u_app.home.HomeScreen;

public class LoginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;

    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();



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

                // Kiểm tra mail
                if (!isValidEmail(email)) {
                    binding.edtEmail.setError("Bạn đã nhập sai email. Vui lòng nhập lại");
                    focusView = binding.edtEmail;
                    cancel = true;
                }

                // Kiểm tra pass
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
                    // trở về HomePage
                    Intent intent = new Intent(LoginPage.this, HomeScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        final EditText edtPass = findViewById(R.id.edtPass);
        final ImageView imvShowPass = findViewById(R.id.imvShowPass);
        binding.imvShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordShowing) {
                    passwordShowing = false;

                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imvShowPass.setImageResource(R.drawable.visibility_showpass);
                }else {
                    passwordShowing = true;

                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imvShowPass.setImageResource(R.drawable.visibility_off);
                }
                //di chuyển cursor
                edtPass.setSelection(edtPass.length());
            }
        });

        binding.txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, ForgotPassScreen.class);
                startActivity(intent);
            }
        });

        binding.txtCreateAcc.setOnClickListener(new View.OnClickListener() {
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