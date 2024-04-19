package com.group2.pop4u_app.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.api.Services.UserService;
import com.group2.model.ResponseValidate;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivitySignUp1Binding;

import java.util.concurrent.CompletableFuture;

public class SignUp_1 extends AppCompatActivity {

    ActivitySignUp1Binding binding;

    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;
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
                String confirmPassword = binding.edtConfirmPass.getText().toString(); // Make sure this matches your XML

                // Reset errors
                binding.edtEmail.setError(null);
                binding.edtPass.setError(null);
                binding.edtConfirmPass.setError(null);

                boolean cancel = false;
                View focusView = null;

                // Check for a valid email address.
                if (!isValidEmail(email)) {
                    binding.edtEmail.setError("Email không hợp lệ.");
                    focusView = binding.edtEmail;
                    cancel = true;
                }

                // Check for a valid password.
                if (!isValidPassword(password)) {
                    binding.edtPass.setError("Mật khẩu phải chứa ít nhất 8 ký tự bao gồm chữ hoa, số và ký tự đặc biệt.");
                    if (focusView == null) {
                        focusView = binding.edtPass;
                    }
                    cancel = true;
                }

                // Check for matching passwords.
                if (!password.equals(confirmPassword)) {
                    binding.edtConfirmPass.setError("Mật khẩu nhập lại không khớp.");
                    if (focusView == null) {
                        focusView = binding.edtConfirmPass;
                    }
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    sendRegisterRequest(email, password);
                }
            }
        });

        final EditText edtPass = findViewById(R.id.edtPass);
        final EditText edtConfirmPass = findViewById(R.id.edtConfirmPass);
        final ImageView imvShowPass = findViewById(R.id.imvShowPass);
        final ImageView imvShowPass2 = findViewById(R.id.imvShowPass2);
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

        binding.imvShowPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conPasswordShowing) {
                    conPasswordShowing = false;

                    edtConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imvShowPass2.setImageResource(R.drawable.visibility_showpass);
                }else {
                    conPasswordShowing = true;

                    edtConfirmPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imvShowPass2.setImageResource(R.drawable.visibility_off);
                }
                //di chuyển cursor
                edtConfirmPass.setSelection(edtConfirmPass.length());
            }
        });

        binding.txtBacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp_1.this.finish();
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

    private void sendRegisterRequest(String email, String password) {
        CompletableFuture<ResponseValidate> future = UserService.instance.register(email, password);
        future.whenComplete((result, error) -> {
            if (error != null) {
                Log.d("Signup level 1", "error happend when register email");
                runOnUiThread(() -> {
                    Toast.makeText(SignUp_1.this, "Đã xảy ra lỗi trong quá trình đăng ký", Toast.LENGTH_SHORT).show();
                });
            } else {
                if (result != null && result.getStatus() == 1) {
                    runOnUiThread(() -> {
                        Toast.makeText(SignUp_1.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp_1.this, SignUp_2.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        if (result != null) {
                            Toast.makeText(SignUp_1.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUp_1.this, "Đã xảy ra lỗi trong quá trình đăng ký", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}