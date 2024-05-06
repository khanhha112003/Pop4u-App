package com.group2.database_helper;

import android.provider.BaseColumns;

public final class OrderContract {
    private OrderContract() {}

    public static class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
    }
}

