package com.group2.pop4u_app.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityQuerySearchBinding;

import java.util.Arrays;
import java.util.List;

public class QuerySearchActivity extends AppCompatActivity {
    ActivityQuerySearchBinding binding;

    HistorySearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQuerySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<String> strings = Arrays.asList("s1", "s2", "s3");
        adapter = new HistorySearchAdapter(this, strings);

        binding.lvHistorySearch.setAdapter(adapter);
    }
}