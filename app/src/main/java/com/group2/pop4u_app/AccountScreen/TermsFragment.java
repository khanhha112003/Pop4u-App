package com.group2.pop4u_app.AccountScreen;

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
        if (!(activity instanceof MainActivity) || binding == null) {
            return;
        }

        MainActivity mainActivity = (MainActivity) activity;
        binding.cdvBuyTerm.setOnClickListener(view -> mainActivity.replaceFragment(new TermDetailFragment("buy")));
        binding.cdvShipTerm.setOnClickListener(view -> mainActivity.replaceFragment(new TermDetailFragment("ship")));
        binding.cdvPayTerm.setOnClickListener(view -> mainActivity.replaceFragment(new TermDetailFragment("pay")));
        binding.cdvPersonalData.setOnClickListener(view -> mainActivity.replaceFragment(new TermDetailFragment("perdata")));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermsBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }
}