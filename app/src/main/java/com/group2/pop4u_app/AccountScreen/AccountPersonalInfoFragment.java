package com.group2.pop4u_app.AccountScreen;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.group2.api.Services.UserService;
import com.group2.model.User;
import com.group2.pop4u_app.databinding.FragmentAccountPersonalInfoBinding;

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

    private void addUserAccountEvents() {
        binding.btnChangeUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(requireContext());

            }
        });

        binding.btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        binding.txtAccountPersonalInfoBirthdate.setText(user.getBirthdate());
        binding.txtAccountPersonalInfoEmail.setText(user.getEmail());
        binding.txtAccountPersonalInfoPhoneNumber.setText(user.getPhone_number());
    }
}