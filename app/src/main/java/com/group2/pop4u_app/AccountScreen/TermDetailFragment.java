package com.group2.pop4u_app.AccountScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.group2.api.Services.ProductService;
import com.group2.model.Product;
import com.group2.pop4u_app.databinding.FragmentTermDetailBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;


public class TermDetailFragment extends Fragment {
    FragmentTermDetailBinding binding;
    private static final String CAT_TYPE = "term_type";
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermDetailBinding.inflate(inflater,container, false);
        loadTermWebView();
        setScreen();
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

    private void loadTermWebView() {
        CompletableFuture<ArrayList<Product>> future = ProductService.instance.getProductByCategory(param_type, null, null, null, null);
        future.thenAccept(products -> {
            binding.wvTermDetail.getSettings().setJavaScriptEnabled(true);
        });

        try {
            future.get();
        } catch (Exception e) {
            Log.d("Term webpage", "Error: " + e.getMessage());
        }
    }
}