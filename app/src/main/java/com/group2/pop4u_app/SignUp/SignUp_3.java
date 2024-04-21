package com.group2.pop4u_app.SignUp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group2.api.Services.UserService;
import com.group2.local.LoginManagerTemp;
import com.group2.model.ResponseValidate;
import com.group2.pop4u_app.databinding.ActivitySignUp3Binding;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SignUp_3 extends AppCompatActivity {

    ActivitySignUp3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnUpdateInfoConfirm.setOnClickListener(v -> sendUpdateInformation());
    }

    private void sendUpdateInformation() {
        String username = getIntent().getStringExtra("username");
        String otp = getIntent().getStringExtra("otp");
        String updateFullName = binding.edtUpdateInfoFullname.getText().toString();
        String updatePhone = binding.edtUpdateInfoPhone.getText().toString();
        String updateGender = binding.edtUpdateInfoGender.getText().toString();
        String updateBirthday = binding.edtUpdateInfoDatebirth.getText().toString();

        if (updateFullName.isEmpty() || updatePhone.isEmpty() || updateGender.isEmpty() || updateBirthday.isEmpty()) {
            Toast.makeText(this, "Please fill all the information", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call API to update information
        CompletableFuture<ResponseValidate> future = UserService.instance.updateProfile(username, otp, updateFullName, updatePhone, updateBirthday);
        future.thenAccept(v -> {
            if (v.getStatus() == 1) {
                Toast.makeText(this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                LoginManagerTemp.isJustFinishRegisterSuccess = true;
                this.finish();
            } else {
                Log.d("Error", "Update information failed");
                Toast.makeText(this, "Update information failed", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            ResponseValidate response = future.get();
        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
        }
    }
}