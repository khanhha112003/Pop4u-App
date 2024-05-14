package com.group2.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.group2.model.SearchHistory;

import java.util.ArrayList;

public class HistorySearchDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "search_history.sqlite";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "search_history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_KEYWORD = "keyword";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public HistorySearchDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
    public void deleteEmptyAndDuplicateSearchHistory() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa các lịch sử tìm kiếm trống
        db.delete(TABLE_NAME, COLUMN_KEYWORD + " = ?", new String[]{""});

        // Xóa các bản ghi trùng lặp, chỉ giữ lại bản ghi mới nhất
        String subquery = "SELECT MIN(" + COLUMN_ID + ") as min_id FROM " + TABLE_NAME + " GROUP BY " + COLUMN_KEYWORD;
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " NOT IN (" + subquery + ")";
        db.execSQL(query);

        db.close();
    }


    // Phương thức để lấy lịch sử tìm kiếm từ cơ sở dữ liệu
    public ArrayList<SearchHistory> getSearchHistory(String query) {
        ArrayList<SearchHistory> searchItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int keywordIndex = cursor.getColumnIndex(COLUMN_KEYWORD);
        int timestampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP);

        if (cursor.moveToFirst()) {
            do {
                if (idIndex >= 0 && keywordIndex >= 0 && timestampIndex >= 0) {
                    Integer searchHistoryId = cursor.getInt(idIndex);
                    String searchHistoryText = cursor.getString(keywordIndex);
                    String timestamp = cursor.getString(timestampIndex);
                    SearchHistory searchItem = new SearchHistory(searchHistoryText, searchHistoryId, timestamp);
                    searchItems.add(searchItem);
                }
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
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_KEYWORD + " LIKE '%" + keyword + "%' ORDER BY " + COLUMN_TIMESTAMP + " DESC";
        return getSearchHistory(query);
    }

    public void deleteSearchHistory(String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_KEYWORD + " = ?", new String[]{keyword});
        db.close();
    }

    public boolean isKeywordExist(String keyword) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_KEYWORD + " = '" + keyword + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public void deleteAllSearchHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
