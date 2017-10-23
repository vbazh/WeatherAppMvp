package com.vbazh.weatherapp_mvp.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "weather.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = " ,";

    private static final String SQL_CREATE_ENTRIES_WEATHER =
            "CREATE TABLE " + DbContract.WeatherEntry.TABLE_NAME + " (" +
                    DbContract.WeatherEntry.COLUMN_ID + TEXT_TYPE + " PRIMARY KEY," +
                    DbContract.WeatherEntry.COLUMN_WEATHER_CITY_ID + INTEGER_TYPE + COMMA_SEP +
                    DbContract.WeatherEntry.COLUMN_WEATHER_CITY_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.WeatherEntry.COLUMN_WEATHER_CITY_LON + REAL_TYPE + COMMA_SEP +
                    DbContract.WeatherEntry.COLUMN_WEATHER_CITY_LAT + REAL_TYPE + COMMA_SEP +
                    DbContract.WeatherEntry.COLUMN_WEATHER_TEMP + REAL_TYPE + COMMA_SEP +
                    DbContract.WeatherEntry.COLUMN_WEATHER_PRESSURE + INTEGER_TYPE + COMMA_SEP +
                    DbContract.WeatherEntry.COLUMN_WEATHER_HUMIDITY + INTEGER_TYPE + " )";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES_WEATHER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
