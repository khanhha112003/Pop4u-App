package com.group2.pop4u_app.AccountScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.group2.pop4u_app.databinding.FragmentSettingWebviewBinding;

public class WebsiteFragment  extends Fragment {
    FragmentSettingWebviewBinding binding;

    public WebsiteFragment() {
    }
    public static TermsFragment newInstance(String param1, String param2) {
        TermsFragment fragment = new TermsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.wvSettingWebview.loadUrl("https://pop4u.vercel.app/about");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingWebviewBinding.inflate(inflater,container, false);
        binding.wvSettingWebview.getSettings().setJavaScriptEnabled(true);
        return binding.getRoot();
    }
}