package com.group2.database_helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.group2.model.Product;

public class OrderDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "orders";
    private static final String DATABASE_NAME = "order.sqlite";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_PRODUCT_PRICE = "productPrice";
    public static final String COLUMN_PRODUCT_CODE = "productCode";
    public static final String COLUMN_QUANTITY = "quantity";

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderContract.OrderEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_PRODUCT_CODE + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_QUANTITY + " INTEGER NOT NULL); ";

        db.execSQL(sql);
    }
    public boolean insertData(Product product,
                              Integer quantity){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, product.getProductName());
        contentValues.put(COLUMN_PRODUCT_CODE, product.getProductCode());
        contentValues.put(COLUMN_PRODUCT_PRICE, product.getProductPrice());
        contentValues.put(COLUMN_QUANTITY, quantity);


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

    public Cursor queryData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return  db.rawQuery(sql, null);
    }
    public boolean excecSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
            return true;
        }
        catch (Exception e){
            Log.e("Error: ", e.toString());
            return false;
        }
    }
    private int numOfRows() {
        Cursor c = queryData("SELECT * FROM " + TABLE_NAME);
        int numOfRows = c.getCount();
        c.close();
        return numOfRows;
    }

    public void createSampleData() {
        if (numOfRows() == 0){
            excecSql("INSERT INTO " + TABLE_NAME + " VALUES(null, 'Aespa – Savage (Photobook Version)', 'PAE1001', 214999, 1)");
            excecSql("INSERT INTO " + TABLE_NAME + " VALUES(null, 'EXO 7th Album [EXIST] (Photo Book Ver.)', 'PEX1001', 214999, 2)");
            excecSql("INSERT INTO " + TABLE_NAME + " VALUES(null, 'ALBUM STRAY KIDS - NOEASY', 'ASK1001', 214999, 3)");
        }
    }
}
