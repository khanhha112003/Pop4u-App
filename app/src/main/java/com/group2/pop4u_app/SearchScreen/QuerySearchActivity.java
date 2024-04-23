package com.group2.pop4u_app.SearchScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.group2.api.Services.SearchService;
import com.group2.model.SearchItem;
import com.group2.pop4u_app.databinding.ActivitySearchScreenQuerySearchBinding;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class QuerySearchActivity extends AppCompatActivity {
    ActivitySearchScreenQuerySearchBinding binding;

    HistorySearchAdapter adapter;

    ArrayList<SearchItem> listSearchRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchScreenQuerySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listSearchRes = new ArrayList<>();
        adapter = new HistorySearchAdapter(this, listSearchRes);

        binding.lvHistorySearch.setAdapter(adapter);

        setSearchBarInitialValue();
        setCancelSearchButton();

        binding.svQuerySearchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                conductSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

    private void conductSearch(String query) {
        // luu history search vao database
        // dien o day

        CompletableFuture<ArrayList<SearchItem>> searchResult = SearchService.instance.search(query);
        searchResult.thenAccept(res -> {
            listSearchRes.clear();
            // de test, neu chen code vao day thi add cai history search luu o database
            listSearchRes.add(new SearchItem(SearchItem.HISTORY_TYPE, query, null, null));
            // dien code lay tu database, dung model Search item lam mau


            listSearchRes.addAll(res);
            adapter.notifyDataSetChanged();
        }).exceptionally(e -> {
            Log.e("Search", "Search failed", e);
            return null;
        });

        try {
            searchResult.get();
        } catch (Exception e) {
            Log.e("Search", "Search failed", e);
        }

    }
}