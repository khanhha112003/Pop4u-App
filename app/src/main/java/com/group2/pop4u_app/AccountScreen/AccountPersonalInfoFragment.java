package com.group2.pop4u_app.AccountScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.group2.api.Services.UserService;
import com.group2.model.User;
import com.group2.pop4u_app.databinding.FragmentAccountPersonalInfoBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AccountPersonalInfoFragment extends Fragment {

    FragmentAccountPersonalInfoBinding binding;

    User user = new User("", "", "", "", "");

    public AccountPersonalInfoFragment() { }

    public static AccountPersonalInfoFragment newInstance() {
        AccountPersonalInfoFragment fragment = new AccountPersonalInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountPersonalInfoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserProfile();
        addUserAccountEvents();
        loadUserAccountInfo();
    }
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private void addUserAccountEvents() {
        binding.btnChangeUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show dialog to choose between gallery and camera
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose an option")
                        .setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        // Take photo
                                        dispatchTakePictureIntent();
                                        break;
                                    case 1:
                                        // Choose from gallery
                                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
                                        break;
                                }
                            }
                        });
                builder.show();
            }
        });

        binding.btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }

            private void saveUserInfo() {
                // Lấy bitmap của hình ảnh người dùng từ ImageView
                BitmapDrawable drawable = (BitmapDrawable) binding.imvUserAccountAvatar.getDrawable();
                Bitmap avatarBitmap = drawable.getBitmap();

                // Chuyển đổi bitmap thành mảng byte
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                avatarBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] avatarByteArray = byteArrayOutputStream.toByteArray();

                // Lưu mảng byte vào cơ sở dữ liệu hoặc SharedPreferences
                // Ví dụ: Giả sử bạn có một phương thức để lưu mảng byte trong SharedPreferences
                saveAvatarToSharedPreferences(avatarByteArray);

                // Tùy chọn, tải ảnh lên máy chủ và lưu URL trong hồ sơ của người dùng
                // Ví dụ: UploadService.uploadImage(avatarBitmap);
            }
            private void saveAvatarToSharedPreferences(byte[] avatarByteArray) {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("avatar", Base64.encodeToString(avatarByteArray, Base64.DEFAULT));
                editor.apply();

                Toast.makeText(requireContext(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        });


        binding.btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Process the captured image
                processImage(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                    // Process the selected image from gallery
                    processImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void processImage(Bitmap bitmap) {
        binding.imvUserAccountAvatar.setImageBitmap(bitmap);
    }
        private void loadUserAccountInfo() {
        // Load user account info
        CompletableFuture<User> userInfoFuture = UserService.instance.getUserProfile();
        userInfoFuture.thenAccept(user -> {
            this.user = user;
            this.setUserProfile();
        });
        try {
            userInfoFuture.get();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Lấy thông tin người dùng lỗi", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUserProfile() {
        binding.edtAccountPersonalInfoLastName.setText(user.getUserLastName());
        binding.edtAccountPersonalInfoFirstName.setText(user.getUserFirstName());
        binding.edtAccountPersonalInfoBirthdate.setText(user.getBirthdate());
        binding.edtAccountPersonalInfoEmail.setText(user.getEmail());
        binding.edtPhoneNumber.setText(user.getPhone_number());
    }
}