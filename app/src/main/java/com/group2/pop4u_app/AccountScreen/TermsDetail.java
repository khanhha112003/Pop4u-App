package com.group2.pop4u_app.AccountScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityTermsDetailBinding;

public class TermsDetail extends AppCompatActivity {
ActivityTermsDetailBinding binding;
    String term_type = "buy";
    String param_type = "buy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbrSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the term type from intent extras
//        term_type = getIntent().getStringExtra("termType");

        // Set up WebView
        binding.wvTermDetail.getSettings().setJavaScriptEnabled(true);
        binding.wvTermDetail.setWebViewClient(new WebViewClient() {
            @Override //for APIs 24 and later
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });

        // Load the corresponding URL based on term type
        setScreen();
    }

    private void setScreen() {
        Intent intent = getIntent();
        term_type = intent.getStringExtra("termID");
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plain_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
