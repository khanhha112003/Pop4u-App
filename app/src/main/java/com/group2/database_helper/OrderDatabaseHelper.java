package com.group2.database_helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.util.Printer;

import com.group2.model.CartItem;
import com.group2.model.Product;

public class OrderDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "orders";
    private static final String DATABASE_NAME = "order.sqlite";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_PRODUCT_PRICE = "productPrice";
    public static final String COLUMN_PRODUCT_COMPARING_PRICE = "productComparingPrice";
    public static final String COLUMN_PRODUCT_CODE = "productCode";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IMAGE = "image";

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_CODE + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
                + COLUMN_PRODUCT_COMPARING_PRICE + " REAL NOT NULL, "
                + COLUMN_IMAGE + " TEXT NOT NULL, "
                + COLUMN_QUANTITY + " INTEGER NOT NULL); ";
        db.execSQL(sql);
    }
    public boolean insertData(Product product,
                              int quantity){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, product.getProductName());
        contentValues.put(COLUMN_PRODUCT_CODE, product.getProductCode());
        contentValues.put(COLUMN_PRODUCT_PRICE, product.getProductPrice());
        contentValues.put(COLUMN_PRODUCT_COMPARING_PRICE, product.getProductComparingPrice());
        contentValues.put(COLUMN_IMAGE, product.getBannerPhoto());
        contentValues.put(COLUMN_QUANTITY, quantity);

        long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    public int updateData(String cartItemCode, int quantity) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_QUANTITY + " = ? "
                + "WHERE " + COLUMN_PRODUCT_CODE + " = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, quantity);
        statement.bindString(2, cartItemCode);
        int rowsUpdated = statement.executeUpdateDelete();
        statement.close();
        database.close();
        return rowsUpdated;
    }

    public int addMoreQuantity(Product product, int quantity) {
        SQLiteDatabase database = getWritableDatabase();
        SQLiteDatabase readDB = getReadableDatabase();
        int currentAmount = 0;
        try (Cursor cursor = readDB.rawQuery("SELECT " + COLUMN_QUANTITY + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_CODE + " = ?", new String[]{String.valueOf(product.getProductCode())})) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    currentAmount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int updatedAmount = quantity + currentAmount;
        String sql = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_QUANTITY + " = ? "
                + "WHERE " + COLUMN_PRODUCT_CODE + " = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, updatedAmount);
        statement.bindString(2, product.getProductCode());
        int rowsUpdated = statement.executeUpdateDelete();
        statement.close();
        database.close();
        return rowsUpdated;
    }

    public boolean undoData(CartItem cartItem) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, cartItem.getName());
        contentValues.put(COLUMN_PRODUCT_CODE, cartItem.getProductCode());
        contentValues.put(COLUMN_PRODUCT_PRICE, cartItem.getPrice());
        contentValues.put(COLUMN_PRODUCT_COMPARING_PRICE, cartItem.getComparingPrice());
        contentValues.put(COLUMN_IMAGE, cartItem.getThumb());
        contentValues.put(COLUMN_QUANTITY, cartItem.getQuantity());

        long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }
    public void deleteAll() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME);
        database.close();
    }

    public int deleteData(String cartItemCode) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_CODE + " = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, cartItemCode);
        int rowsUpdated = statement.executeUpdateDelete();
        statement.close();
        database.close();
        return rowsUpdated;
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
