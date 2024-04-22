package com.group2.pop4u_app.AccountScreen;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.pop4u_app.databinding.FragmentAccountPersonalInfoBinding;

public class AccountPersonalInfoFragment extends Fragment {

    FragmentAccountPersonalInfoBinding binding;

    public AccountPersonalInfoFragment() { }

    public static AccountPersonalInfoFragment newInstance(String param1, String param2) {
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
        addUserAccountEvents();
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

}