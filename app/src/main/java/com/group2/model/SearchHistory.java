package com.group2.model;

public class SearchHistory {
    private String searchHistory;
    private Integer id;

    public SearchHistory(String searchHistory, Integer id) {
        this.searchHistory = searchHistory;
        this.id = id;
    }

    public SearchItem toSearchItem() {
        return new SearchItem(SearchItem.HISTORY_TYPE,searchHistory, null, null);
    }
}
