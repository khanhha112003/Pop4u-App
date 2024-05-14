package com.group2.pop4u_app.SearchScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.group2.api.Services.ProductService;
import com.group2.api.Services.SearchService;
import com.group2.database_helper.HistorySearchDatabaseHelper;
import com.group2.model.Product;
import com.group2.model.SearchHistory;
import com.group2.model.SearchItem;
import com.group2.pop4u_app.ArtistInfoScreen.ArtistInfoScreen;
import com.group2.pop4u_app.ProductDetailScreen.ProductDetailScreen;
import com.group2.pop4u_app.databinding.ActivitySearchScreenQuerySearchBinding;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class QuerySearchActivity extends AppCompatActivity {
    ActivitySearchScreenQuerySearchBinding binding;

    HistorySearchAdapter adapter;

    ArrayList<SearchItem> listSearchRes = new ArrayList<>();

    ArrayList<SearchHistory> searchHistoriesFromDatabase = new ArrayList<>();

    HistorySearchDatabaseHelper historySearchDatabaseHelper;

    private int waitingTime = 100;
    private CountDownTimer cntr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historySearchDatabaseHelper = new HistorySearchDatabaseHelper(this);
        binding = ActivitySearchScreenQuerySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listSearchRes = new ArrayList<>();
        adapter = new HistorySearchAdapter(this, listSearchRes);

        adapter.listener = new HistorySearchAdapter.AdapterEventListener() {
            @Override
            public void onDeleteHistorySearch(SearchItem searchItem) {
                // Xóa history search trong database
                Log.d("Search Screen", "Delete history search: " + searchItem.getItemContext());
                historySearchDatabaseHelper.deleteSearchHistory(searchItem.getItemContext());
            }

            @Override
            public void onTapSearchItem(SearchItem searchItem) {
                // Thiết lập giá trị của SearchView khi nhấp vào mục lịch sử tìm kiếm
                binding.svQuerySearchBox.setQuery(searchItem.getItemContext(), false);
                // Gọi phương thức conductSearch để tìm kiếm
                Log.d("Search Screen", "Tap search item: " + searchItem.getItemContext());
                if (Objects.equals(searchItem.getItemType(), SearchItem.HISTORY_TYPE)) {
                    conductSearch(searchItem.getItemContext());
                } else if (Objects.equals(searchItem.getItemType(), SearchItem.ARTIST_TYPE)) {
                    openArtistDetail(searchItem.getItemCode());
                } else {
                    // Thêm code khi click vào một item search suggest
                    openProductDetail(searchItem.getItemCode());
                }
            }
        };

        binding.lvHistorySearch.setAdapter(adapter);

        setSearchBarInitialValue();
        setCancelSearchButton();

        binding.svQuerySearchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                conductSearch(query);
                // Lưu history search vào database
                if (!historySearchDatabaseHelper.isKeywordExist(query)) {
                    historySearchDatabaseHelper.addSearchHistory(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Hiển thị suggest search, lấy suggest search từ db
                if (cntr != null) {
                    cntr.cancel();
                }
                cntr = new CountDownTimer(waitingTime, 500) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        searchHistoriesFromDatabase.clear();
                        listSearchRes.clear();
                        searchHistoriesFromDatabase = historySearchDatabaseHelper.getSearchHistoryByMatchingKeyword(newText);
                        if (searchHistoriesFromDatabase.size() == 0) {
                            searchHistoriesFromDatabase = historySearchDatabaseHelper.getRecentSearchHistory();
                        }
                        for (SearchHistory searchHistory : searchHistoriesFromDatabase) {
                            if (!searchHistory.getKeyword().isEmpty() && !isItemDuplicate(searchHistory)) {
                                listSearchRes.add(searchHistory.toSearchItem());
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                };
                cntr.start();
                return false;
            }
        });
    }

    private void setSearchBarInitialValue() {
        String newString;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            newString = "";
        } else {
            newString = extras.getString("start_character");
        }
        binding.svQuerySearchBox.setQuery(newString, false);
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
        if (!historySearchDatabaseHelper.isKeywordExist(query)) {
            historySearchDatabaseHelper.addSearchHistory(query);
        }
        searchHistoriesFromDatabase.clear();
        searchHistoriesFromDatabase = historySearchDatabaseHelper.getSearchHistoryByMatchingKeyword(query);
        listSearchRes.clear();
        for (SearchHistory searchHistory : searchHistoriesFromDatabase) {
            listSearchRes.add(searchHistory.toSearchItem());
        }
        CompletableFuture<ArrayList<SearchItem>> searchResult = SearchService.instance.search(query);
        searchResult.thenAccept(res -> {
            listSearchRes.addAll(res);
            adapter.notifyDataSetChanged();
        }).exceptionally(e -> {
            Log.e("Search", "Search failed", e);
            adapter.notifyDataSetChanged();
            return null;
        });

        try {
            searchResult.get();
        } catch (Exception e) {
            adapter.notifyDataSetChanged();
            Log.e("Search", "Search failed", e);
        }
    }

    private void openArtistDetail(String artistCode) {
        Intent intent = new Intent(QuerySearchActivity.this, ArtistInfoScreen.class);
        intent.putExtra("artistCode", artistCode);
        startActivity(intent);
        finish();
    }

    private void openProductDetail(String productCode) {
        CompletableFuture<Product> product = ProductService.instance.getProduct(productCode);
        product.thenAccept(res -> {
            Intent intent = new Intent(QuerySearchActivity.this, ProductDetailScreen.class);
            intent.putExtra("productCode", productCode);
            intent.putExtra("artistCode", res.getArtistCode());
            startActivity(intent);
            finish();
        }).exceptionally(e -> {
            Log.e("Search", "Get product failed", e);
            return null;
        });
        try {
            product.get();
        } catch (Exception e) {
            Log.e("Search", "Get product failed", e);
        }
    }

    private boolean isItemDuplicate(SearchHistory searchHistory) {
        for (SearchItem item : listSearchRes) {
            if (item.getItemContext().equals(searchHistory.getKeyword())) {
                return true;
            }
        }
        return false;
    }
}
