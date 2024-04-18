package com.group2.database_helper;

import static com.group2.database_helper.LoginContract.LoginEntry.COLUMN_PASSWORD;
import static com.group2.database_helper.OrderContract.OrderEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "login.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String COLUMN_USERNAME = "gmail";

    public LoginDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + LoginContract.LoginEntry.TABLE_NAME + " ("
                + LoginContract.LoginEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LoginContract.LoginEntry.COLUMN_GMAIL + " TEXT NOT NULL, "
                + LoginContract.LoginEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LoginContract.LoginEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String gmail,
                              String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, gmail);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Returns true if insert was successful
    }

    public Cursor getData(String sql){
        try {
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }
        catch (Exception e){
            return null;
        }
    }

    //INSERT, UPDATE, DELETE
    public void execSql(String sql){
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
        }
        catch (Exception e){
        }
    }

    //Num of Rows
    public int numbOfRows(){
        Cursor cursor = getData("SELECT * FROM " + TABLE_NAME);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
