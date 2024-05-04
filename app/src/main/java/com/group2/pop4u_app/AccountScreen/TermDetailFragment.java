package com.group2.pop4u_app.AccountScreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import com.group2.pop4u_app.databinding.FragmentTermDetailBinding;


public class TermDetailFragment extends Fragment {
    FragmentTermDetailBinding binding;
    String term_type = "buy";
    String param_type = "buy";
    public TermDetailFragment(String termType) {
        this.term_type = termType;
    }

    public static TermDetailFragment newInstance(String termType) {
        TermDetailFragment fragment = new TermDetailFragment(termType);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermDetailBinding.inflate(inflater,container, false);
        setScreen();
        binding.wvTermDetail.getSettings().setJavaScriptEnabled(true);
        binding.wvTermDetail.setWebViewClient(new WebViewClient(){
            @Override //for APIs 24 and later
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                return true;
            }
        });
        return binding.getRoot();
    }

    private void setScreen() {
        if ("buy".equals(term_type)) {
            param_type = "Buy";
            binding.wvTermDetail.loadUrl("https://pop4u.vercel.app/buy_policy");
        } else if ("ship".equals(term_type)) {
            param_type = "Ship";
            binding.wvTermDetail.loadUrl("https://pop4u.vercel.app/ship_policy");
        } else if ("pay".equals(term_type)) {
            param_type = "Pay";
            binding.wvTermDetail.loadUrl("https://pop4u.vercel.app/payment_policy");
        } else if ("perdata".equals(term_type)) {
            param_type = "PerData";
            binding.wvTermDetail.loadUrl("https://pop4u.vercel.app/personal_data");
        }
    }
}