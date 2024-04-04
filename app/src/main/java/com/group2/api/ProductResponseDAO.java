package com.group2.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;

public class ProductResponseDAO {
    @JsonProperty("total")
    private int total;

    @JsonProperty("list_product")
    private Collection<ProductDAO> productList;

    public Collection<ProductDAO> getProductList() {
        return productList;
    }
}
