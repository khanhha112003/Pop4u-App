package com.group2.database_helper;

import android.provider.BaseColumns;

public final class OrderContract {
    private OrderContract() {}

    public static class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_PRICE = "product_price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_TOKEN = "token";
    }
}

