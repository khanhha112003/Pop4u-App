package com.group2.pop4u_app.LoginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group2.api.Services.UserService;
import com.group2.database_helper.HistorySearchDatabaseHelper;
import com.group2.database_helper.LoginDatabaseHelper;
import com.group2.local.LoginManagerTemp;
import com.group2.pop4u_app.MainActivity;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.SignUp.SignUp_1;
import com.group2.pop4u_app.databinding.ActivityLoginPageBinding;

import java.util.concurrent.CompletableFuture;

public class LoginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;

    LoginDatabaseHelper loginDatabaseHelper;

    HistorySearchDatabaseHelper historySearchDatabaseHelper;
    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginDatabaseHelper = new LoginDatabaseHelper(this);
        historySearchDatabaseHelper = new HistorySearchDatabaseHelper(this);
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
                   loginDatabaseHelper.insertData(v);
                   historySearchDatabaseHelper.deleteAllSearchHistory();
                   LoginManagerTemp.isJustFinishLoginSuccess = true;
                   this.finish();
               }
           } else {
                binding.btnLogIn.setClickable(true);
                Log.d("Login screen", "Login fail");
                Toast.makeText(this, "Thông tin đăng nhập không chính xác, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
           }
        });
        try {
            loginFuture.get();
        } catch (Exception e) {
            binding.btnLogIn.setClickable(true);
            Log.d("Login screen", "Login fail");
            Toast.makeText(this, "Đã có lỗi xảy ra, xin vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvents() {
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtUsername.getText().toString();
                String password = binding.edtPass.getText().toString();
                binding.btnLogIn.setClickable(false);
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

        binding.txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, ForgotPassScreen.class);
                startActivity(intent);
                if (LoginPage.this != null && !LoginPage.this.isFinishing()) {
                    LoginPage.this.finish();
                }
            }
        });

        //Show password
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
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private boolean isValidPassword(String password) {
        // Password regex pattern
        return password.length() > 7;
    }

}