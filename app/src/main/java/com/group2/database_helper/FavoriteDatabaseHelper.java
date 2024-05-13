package com.group2.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.group2.model.Product;

public class FavoriteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pop4u.sqlite";
    public static final String TABLE_NAME = "favorite";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_PRODUCT_PRICE = "productPrice";
    public static final String COLUMN_SALE_PERCENT = "productSalePercent";
    public static final String COLUMN_PRODUCT_CODE = "productCode";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_ARTIST_CODE = "artistCode";
    public static final String COLUMN_IMAGE = "image";


    public FavoriteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_CODE + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
                + COLUMN_SALE_PERCENT + " REAL NOT NULL, "
                + COLUMN_IMAGE + " TEXT NOT NULL, "
                + COLUMN_ARTIST_CODE + " TEXT NOT NULL, "
                + COLUMN_ARTIST + " TEXT NOT NULL); ";
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
        contentValues.put(COLUMN_PRODUCT_NAME, product.getProductName());
        contentValues.put(COLUMN_PRODUCT_CODE, product.getProductCode());
        contentValues.put(COLUMN_PRODUCT_PRICE, product.getProductPrice());
        contentValues.put(COLUMN_SALE_PERCENT, product.getProductSalePercent());
        contentValues.put(COLUMN_IMAGE, product.getBannerPhoto());
        contentValues.put(COLUMN_ARTIST, product.getProductArtistName());
        contentValues.put(COLUMN_ARTIST_CODE, product.getArtistCode());

        long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    public int deleteData(String favoriteItemCode) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_CODE + " = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, favoriteItemCode);
        int rowsUpdated = statement.executeUpdateDelete();
        statement.close();
        database.close();
        return rowsUpdated;
    }

    public boolean checkData(String favoriteItemCode) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            // Define the query
            String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_CODE  + " = ?";

            cursor = database.rawQuery(query, new String[]{favoriteItemCode});

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
