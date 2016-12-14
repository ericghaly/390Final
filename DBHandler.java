package com.example.ryan.bananafinder;

/**
 * Created by Ryan on 12/7/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 15;
    // Database Name
    private static final String DATABASE_NAME = "bananaInfo";
    // Contacts table name
    private static final String TABLE_BANANA = "banana";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_LONG = "longitude";
    private static final String KEY_LAT = "latitude";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BANANA_TABLE = "CREATE TABLE " + TABLE_BANANA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AMOUNT + " INTEGER,"
                + KEY_LONG + " REAL," + KEY_LAT + " REAL" + ")";
        db.execSQL(CREATE_BANANA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANANA);
        // Creating tables again
        onCreate(db);
    }

    // Adding new shop
    public void addBanana(Banana banana) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, banana.getId());
        values.put(KEY_AMOUNT, banana.getAmount()); // Amount of Bananas
        values.put(KEY_LONG, banana.getLongitude()); // Banana longitude
        values.put(KEY_LAT, banana.getLatitude()); // Banana latitude
        // Inserting Row
        db.insert(TABLE_BANANA, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Bananas
    public List<Banana> getAllBananas() {
        List<Banana> bananaList = new ArrayList<Banana>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BANANA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Banana banana = new Banana();
                banana.setId(Integer.parseInt(cursor.getString(0)));
                banana.setAmount(cursor.getInt(1));
                banana.setLongitude(cursor.getDouble(2));
                banana.setLatitude(cursor.getDouble(3));
                // Adding contact to list
                bananaList.add(banana);
            } while (cursor.moveToNext());
        }
        // return contact list
        return bananaList;
    }


    // Getting banana Count
    public int getBananaCount() {
        String countQuery = "SELECT * FROM " + TABLE_BANANA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //int cnt = cursor.getCount();
       // cursor.close();
        // return count
        return cursor.getCount();
    }

  /*  public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BANANA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }*/

    // Updating a shop
    public int updateBanana(Banana banana) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, banana.getAmount());
        values.put(KEY_LONG, banana.getLongitude());
        values.put(KEY_LAT, banana.getLatitude());
        // updating row
        return db.update(TABLE_BANANA, values, KEY_ID + " = ?",
                new String[]{String.valueOf(banana.getId())});
    }


    // Deleting a banana
    public void deleteBanana(Banana banana) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BANANA, KEY_ID + " = ?",
                new String[] { String.valueOf(banana.getId()) });
        db.close();
    }

}

