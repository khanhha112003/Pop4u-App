package com.group2.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.group2.model.Product;

public class AvatarUserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "avatarUser.sqlite";
    public static final String TABLE_NAME = "avatar";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_IMAGE = "userImage";

    public AvatarUserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_IMAGE + " TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(Product product){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE, product.getBannerPhoto());
        long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }
    public int deleteData(String ImageCode) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_IMAGE + " = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, ImageCode);
        int rowsUpdated = statement.executeUpdateDelete();
        statement.close();
        database.close();
        return rowsUpdated;
    }
    public boolean checkData(String ImageCode) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            // Define the query
            String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_IMAGE  + " = ?";

            cursor = database.rawQuery(query, new String[]{ImageCode});

            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                exists = count > 0;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return exists;
    }
}
