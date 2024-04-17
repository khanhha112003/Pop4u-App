package com.group2.database_helper;

import static com.group2.database_helper.OrderContract.OrderEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "order.db";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOKEN = "token";

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderContract.OrderEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_TOKEN + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_ORDER_TABLE);
    }
    public boolean insertData(String product_name,
                              int product_price,
                              int quantity,
                              String token){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, product_name);
        contentValues.put(COLUMN_PRODUCT_PRICE, product_price);
        contentValues.put(COLUMN_QUANTITY, quantity);
        contentValues.put(COLUMN_TOKEN, token);


        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Returns true if insert was successful
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
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
