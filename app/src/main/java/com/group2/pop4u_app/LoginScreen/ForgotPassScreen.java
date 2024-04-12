package com.group2.pop4u_app.LoginScreen;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityForgotPassScreenBinding;

public class ForgotPassScreen extends AppCompatActivity {

    ActivityForgotPassScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPassScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvent();


    }

    private void addEvent() {
        String email = binding.edtEmail.getText().toString();
        binding.edtEmail.setError(null);


        boolean cancel = false;
        View focusView = null;

        if (!isValidEmail(email)) {
            binding.edtEmail.setError("Bạn đã nhập sai email. Vui lòng nhập lại");
            focusView = binding.edtEmail;
            cancel = true;
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}