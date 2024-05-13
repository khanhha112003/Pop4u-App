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

        adapter.listener = new HistorySearchAdapter.AdapterEventListener() {
            @Override
            public void onDeleteHistorySearch(SearchItem searchItem) {
                // them code xoa history search trong database
                Log.d("Search Screen", "Delete history search: " + searchItem.getItemContext());
            }

            @Override
            public void onTapSearchItem(SearchItem searchItem) {
                // them code khi click vao 1 item search
                Log.d("Search Screen", "Tap search item: " + searchItem.getItemContext());
            }
        };

        binding.lvHistorySearch.setAdapter(adapter);

        setSearchBarInitialValue();
        setCancelSearchButton();

        binding.svQuerySearchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                conductSearch(query);
                // them code de luu history search vao database
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // them code de hien thi suggest search, lay suggest search tu db
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
            // khi code phan local search thi dung logic tuong tu o cho nay
            listSearchRes.clear();
            // xoa dong nay di khi chen code that
            listSearchRes.add(new SearchItem(SearchItem.HISTORY_TYPE, query, null, null));
            // dien code lay tu database, dung model Search item lam mau

            // giai thich: res la ket qua search tu server, moi item la 1 ket qua search
            // moi item co type la SearchItem.ARTIST_TYPE hoac SearchItem.SONG_TYPE
            // moi item co id la id cua artist hoac song
            // khi get ve roi thi lay adapter de doi listSearchRes

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