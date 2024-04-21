package com.group2.pop4u_app.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.group2.model.SearchItem;
import com.group2.pop4u_app.databinding.SearchScreenActivityQuerySearchBinding;

import java.util.Arrays;
import java.util.List;

public class QuerySearchActivity extends AppCompatActivity {
    SearchScreenActivityQuerySearchBinding binding;

    HistorySearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = SearchScreenActivityQuerySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<SearchItem> listSearchRes = Arrays.asList(
                new SearchItem(SearchItem.HISTORY_TYPE, "History 1", ""),
                new SearchItem(SearchItem.HISTORY_TYPE, "History 2", ""),
                new SearchItem(SearchItem.HISTORY_TYPE, "History 3", "")
        );
        adapter = new HistorySearchAdapter(this, listSearchRes);

        binding.lvHistorySearch.setAdapter(adapter);

        setSearchBarInitialValue();
        setCancelSearchButton();
    }

    private void setSearchBarInitialValue() {
        String newString;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= "";
        } else {
            newString= extras.getString("start_character");
        }
        binding.svQuerySearchBox.setQuery(newString,false);
    }

    private void setCancelSearchButton() {
        binding.txtCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void conductSearch() {
        String query = binding.svQuerySearchBox.getQuery().toString();

    }
}