package com.group2.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.group2.model.Address;
import java.util.ArrayList;

public class LocationDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "location.sqlite";
    private static final String TABLE_NAME = "location";
    private static final int DATABASE_VERSION = 1;
    private static final String _ID = "id";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_NAME = "name";

    public LocationDatabaseHelper(Context context) {
        super(context, LocationDatabaseHelper.DATABASE_NAME, null, LocationDatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + LocationDatabaseHelper.TABLE_NAME + " ("
                + LocationDatabaseHelper._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LocationDatabaseHelper.COLUMN_ADDRESS + " TEXT NOT NULL, "
                + LocationDatabaseHelper.COLUMN_PHONE + " TEXT NOT NULL, "
                + LocationDatabaseHelper.COLUMN_NAME + " TEXT NOT NULL); ";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocationDatabaseHelper.TABLE_NAME);
        onCreate(db);
    }

    public Cursor queryData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return  db.rawQuery(sql, null);
    }

    public boolean insertData(Address address){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationDatabaseHelper.COLUMN_ADDRESS, address.getCus_address());
        contentValues.put(LocationDatabaseHelper.COLUMN_PHONE, address.getCus_phone());
        contentValues.put(LocationDatabaseHelper.COLUMN_NAME, address.getCus_name());
        long result = database.insert(LocationDatabaseHelper.TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    public ArrayList<Address> getAllAddress () {
        ArrayList<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM " + LocationDatabaseHelper.TABLE_NAME;
        try (Cursor cursor = queryData(sql)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String address = cursor.getString(1);
                    String phone = cursor.getString(2);
                    String name = cursor.getString(3);
                    addresses.add(new Address(name, phone, address));
                } while (cursor.moveToNext());
            }
        }
        return addresses;
    }

    public int numOfRows() {
        Cursor c = queryData("SELECT * FROM " + TABLE_NAME);
        int numOfRows = c.getCount();
        c.close();
        return numOfRows;
    }

    public boolean clearAllData(){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(LocationDatabaseHelper.TABLE_NAME, null, null) > 0;
    }
}
