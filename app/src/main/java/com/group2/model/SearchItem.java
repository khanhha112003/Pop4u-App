package com.group2.model;

public class SearchItem {

    public static final String HISTORY_TYPE = "history_search";
    public static final String ARTIST_TYPE = "artist";
    public static final String PRODUCT_TYPE = "product";
    private String itemType;
    private String itemContext;
    private String itemImage;

    public String getItemType() {
        return itemType;
    }

    public String getItemContext() {
        return itemContext;
    }

    public String getItemImage() {
        return itemImage;
    }

    public SearchItem(String itemType, String itemContext, String itemImage) {
        this.itemType = itemType;
        this.itemContext = itemContext;
        this.itemImage = itemImage;
    }
}
