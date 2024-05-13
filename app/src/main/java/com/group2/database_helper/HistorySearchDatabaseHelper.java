package com.group2.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.group2.pop4u_app.SearchScreen.HistorySearchAdapter;

public class HistorySearchDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "search_history.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "search_history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_KEYWORD = "keyword";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public HistorySearchDatabaseHelper(@Nullable HistorySearchAdapter.AdapterEventListener context) {
        super((Context) context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo bảng lưu trữ lịch sử tìm kiếm
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_KEYWORD + " TEXT," +
                COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Các bước nâng cấp cơ sở dữ liệu, nếu cần
        // Các bước này có thể là xóa bảng cũ và tạo lại nó hoặc thêm các cột mới, tùy thuộc vào yêu cầu của bạn
    }

    // Phương thức để thêm một mục lịch sử tìm kiếm mới vào cơ sở dữ liệu
    public void addSearchHistory(String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEYWORD, keyword);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // (Tùy chọn) Phương thức để lấy lịch sử tìm kiếm từ cơ sở dữ liệu
    public Cursor getSearchHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TIMESTAMP + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
