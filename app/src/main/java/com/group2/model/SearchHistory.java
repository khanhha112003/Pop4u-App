package com.group2.model;

public class SearchHistory {
    private String keyword;
    private int id;
    private String timestamp;


    // Constructor mới
    public SearchHistory(String keyword, int id, String timestamp) {
        this.keyword = keyword;
        this.id = id;
        this.timestamp = timestamp;
    }

    // Getter và Setter cho các thuộc tính
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public SearchItem toSearchItem() {
        return new SearchItem(SearchItem.HISTORY_TYPE, keyword, null, null);
    }
}
