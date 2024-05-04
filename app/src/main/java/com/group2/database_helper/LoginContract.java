package com.group2.database_helper;

import android.provider.BaseColumns;

public final class LoginContract {
    private LoginContract() {}

    public static class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "credentials";
        public static final String COLUMN_TOKEN = "token";
    }
}

