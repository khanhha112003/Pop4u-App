package com.group2.pop4u_app.LoginScreen;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityCreateNewPassBinding;

public class CreateNewPass extends AppCompatActivity {

    ActivityCreateNewPassBinding binding;
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityCreateNewPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = binding.edtPass.getText().toString();
                String confirmPassword = binding.edtPassAgain.getText().toString(); // Make sure this matches your XML

                binding.edtPass.setError(null);
                binding.edtPassAgain.setError(null);

                boolean cancel = false;
                View focusView = null;

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
                    binding.edtPassAgain.setError("Mật khẩu nhập lại không khớp.");
                    if (focusView == null) {
                        focusView = binding.edtPassAgain;
                    }
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
            }
            }
        });
        final EditText edtPass = findViewById(R.id.edtPass);
        final EditText edtPassAgain = findViewById(R.id.edtPassAgain);
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

                    edtPassAgain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imvShowPass2.setImageResource(R.drawable.visibility_showpass);
                }else {
                    conPasswordShowing = true;

                    edtPassAgain.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imvShowPass2.setImageResource(R.drawable.visibility_off);
                }
                //di chuyển cursor
                edtPassAgain.setSelection(edtPassAgain.length());
            }
        });


    }
    private boolean isValidPassword(String password) {
        // Check if the password contains at least 8 characters, includes at least one uppercase letter, one number, and one special character
        String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*-.,/()_])(?=\\S+$).{8,}";
        return password.matches(passwordPattern);
    }
}