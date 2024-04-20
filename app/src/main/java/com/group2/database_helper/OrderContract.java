package com.group2.database_helper;

import android.provider.BaseColumns;

public final class OrderContract {
    private OrderContract() {}

    public static class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_PRODUCT_PRICE = "productPrice";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_CODE = "productCode";
    }
}

