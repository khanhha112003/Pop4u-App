package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.SearchItem;

public class SearchItemDAO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("image")
    private String image;
    @JsonProperty("type_item")
    private String type_item;

    public SearchItem asSearchItem() {
        if (type_item.equals("product")) {
            return new SearchItem(SearchItem.PRODUCT_TYPE, name, image, code);
        } else if (type_item.equals("artist")) {
            return new SearchItem(SearchItem.ARTIST_TYPE, name, image, code);
        } else {
            return new SearchItem(SearchItem.HISTORY_TYPE, name, "", "");
        }
    }
}
