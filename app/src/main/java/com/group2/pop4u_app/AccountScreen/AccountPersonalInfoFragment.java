package com.group2.pop4u_app.AccountScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AccountPersonalInfoFragment extends Fragment {

    private FragmentAccountPersonalInfoBinding binding;
    private User user = new User("", "", "", "", "");
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    public AccountPersonalInfoFragment() {}

    public static AccountPersonalInfoFragment newInstance() {
        return new AccountPersonalInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountPersonalInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserProfile();
        addUserAccountEvents();
        loadUserAccountInfo();
        loadImageFromStorage();
    }

    private void addUserAccountEvents() {
        binding.btnChangeUserAvatar.setOnClickListener(view -> {
            // Show dialog to choose between gallery and camera
            new AlertDialog.Builder(requireContext())
                    .setTitle("Choose an option")
                    .setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, (dialogInterface, i) -> {
                        if (i == 0) {
                            dispatchTakePictureIntent();
                        } else if (i == 1) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
                        }
                    })
                    .show();
        });

        binding.btnSaveInfo.setOnClickListener(view -> saveUserInfo());
        binding.btnDeleteAccount.setOnClickListener(view -> {
            // Handle account deletion here
        });
    }

    private void saveUserInfo() {
        // Get the Bitmap from ImageView
        BitmapDrawable drawable = (BitmapDrawable) binding.imvUserAccountAvatar.getDrawable();
        Bitmap avatarBitmap = drawable.getBitmap();
        saveToInternalStorage(avatarBitmap);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(requireContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the Bitmap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Toast.makeText(requireContext(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage() {
        ContextWrapper cw = new ContextWrapper(requireContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File f = new File(directory, "profile.jpg");

        if (f.exists()) {
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            binding.imvUserAccountAvatar.setImageBitmap(b);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                processImage(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                    processImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processImage(Bitmap bitmap) {
        binding.imvUserAccountAvatar.setImageBitmap(bitmap);
        saveToInternalStorage(bitmap); // Save the new avatar image
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
