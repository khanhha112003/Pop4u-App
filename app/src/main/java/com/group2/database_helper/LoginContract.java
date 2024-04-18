package com.group2.database_helper;

import android.provider.BaseColumns;

public final class LoginContract {
    private LoginContract() {}

    public static class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "login";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_GMAIL = "gmail";
    }
}

