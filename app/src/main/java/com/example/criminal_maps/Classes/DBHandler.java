package com.example.criminal_maps.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "crimes.db";
    public static final String TABLE_CRIMES = "crimes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "crime_date";
    public static final String COLUMN_TYPE = "crime_type";
    public static final String COLUMN_REPORT = "report";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CRIMES_TABLE = "CREATE TABLE " + TABLE_CRIMES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_LONGITUDE + " REAL, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_REPORT + " TEXT " + ")";
        db.execSQL(CREATE_CRIMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRIMES);
        onCreate(db);
    }

    public void addCrime(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, crime.getId());
        values.put(COLUMN_LONGITUDE, crime.getLongitude());
        values.put(COLUMN_LATITUDE, crime.getLatitude());
        values.put(COLUMN_NAME, crime.getName());
        values.put(COLUMN_DATE, crime.getDate());
        values.put(COLUMN_TYPE, crime.getType());
        values.put(COLUMN_REPORT, crime.getReport());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CRIMES, null, values);
    }

    public ArrayList<Crime> getAllCrimes() {
        String query = "SELECT * FROM " + TABLE_CRIMES;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Crime> crimes = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));
            double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            int type = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
            String report = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT));
            Crime crime = new Crime(id, longitude, latitude, name, date, type, report);
            crimes.add(crime);
        }
        cursor.close();
        db.close();

        return crimes;
    }
}
