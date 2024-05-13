package com.group2.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.group2.model.SearchHistory;
import com.group2.model.SearchItem;
import com.group2.pop4u_app.SearchScreen.HistorySearchAdapter;

import java.util.ArrayList;

public class HistorySearchDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pop4u.sqlite";
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

    public

    // (Tùy chọn) Phương thức để lấy lịch sử tìm kiếm từ cơ sở dữ liệu
    ArrayList<SearchHistory> getSearchHistory(String query) {
        ArrayList<SearchHistory> searchItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Integer searchHistoryId = cursor.getInt(0);
                String searchHistoryText = cursor.getString(1);
                SearchHistory searchItem = new SearchHistory(searchHistoryText, searchHistoryId);
                searchItems.add(searchItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return searchItems;
    }

    public ArrayList<SearchHistory> getRecentSearchHistory() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TIMESTAMP + " DESC LIMIT 5";
        return getSearchHistory(query);
    }

    public ArrayList<SearchHistory> getSearchHistoryByMatchingKeyword(String keyword) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_KEYWORD + " LIKE '%" + keyword + "%'";
        return getSearchHistory(query);
    }

    public void deleteSearchHistory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void insertData(SearchItem searchItem) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_KEYWORD, searchItem.getItemContext());
        long result = database.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            // Insert failed
            Log.d("Database search", "Insert failed");
        } else {
            // Insert successful
            Log.d("Database search", "Insert success");
        }
        database.close();
    }
}
