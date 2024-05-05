package com.group2.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.group2.local.LoginManagerTemp;

public class LoginDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "login.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String COLUMN_TOKEN = "token";

    public LoginDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + LoginContract.LoginEntry.TABLE_NAME + " ("
                + LoginContract.LoginEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LoginContract.LoginEntry.COLUMN_TOKEN + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LoginContract.LoginEntry.TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String token){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TOKEN, token);
        database.insert(LoginContract.LoginEntry.TABLE_NAME, null, contentValues);
        database.close();
    }

    public void clearAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(LoginContract.LoginEntry.TABLE_NAME, null, null);
    }

    public Boolean syncToken(){
        try (Cursor cursor = queryData("SELECT * FROM " + LoginContract.LoginEntry.TABLE_NAME)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String token = cursor.getString(1);
                    LoginManagerTemp.setToken(token);
                    return true;
                } while (cursor.moveToNext());
            } else {
                LoginManagerTemp.setToken(null);
            }
        } catch (Exception e) {
            LoginManagerTemp.setToken(null);
        }
        return false;
    }
    public Cursor queryData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return  db.rawQuery(sql, null);
    }
}
