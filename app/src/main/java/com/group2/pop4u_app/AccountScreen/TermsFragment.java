package com.group2.pop4u_app.AccountScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.group2.pop4u_app.MainActivity;
import com.group2.pop4u_app.databinding.FragmentTermsBinding;

public class TermsFragment extends Fragment {
    FragmentTermsBinding binding;
    public TermsFragment() {
        // Required empty public constructor
    }
    public static TermsFragment newInstance(String param1, String param2) {
        TermsFragment fragment = new TermsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        setTabHandler();
        super.onStart();
    }

    private void setTabHandler() {
        FragmentActivity activity = getActivity();
        if (!(activity instanceof SettingScreen) || binding == null) {
            return;
        }

        SettingScreen settingActivity = (SettingScreen) activity;
        binding.cdvBuyTerm.setOnClickListener(view -> openTerm("buy"));
        binding.cdvShipTerm.setOnClickListener(view -> openTerm("ship"));
        binding.cdvPayTerm.setOnClickListener(view -> openTerm("pay"));
        binding.cdvPersonalData.setOnClickListener(view -> openTerm("perdata"));
    }

    private void openTerm(String termID){
        Intent intent= new Intent(requireActivity(),TermsDetail.class);
        intent.putExtra("termID",termID);
        startActivity(intent);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermsBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }
}