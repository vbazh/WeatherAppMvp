package com.vbazh.weatherapp_mvp.data.repository.cities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.vbazh.weatherapp_mvp.data.entities.City;
import com.vbazh.weatherapp_mvp.data.repository.DbContract;
import com.vbazh.weatherapp_mvp.data.repository.DbHelper;
import com.vbazh.weatherapp_mvp.utils.BaseUtils;

public class CitiesLocalDataSource implements CityDataSource {

    private static CitiesLocalDataSource INSTANCE;

    private DbHelper mDbHelper;

    String[] projection = {
            DbContract.CityEntry.COLUMN_ID,
            DbContract.CityEntry.COLUMN_CITY_ID,
            DbContract.CityEntry.COLUMN_CITY_NAME,
            DbContract.CityEntry.COLUMN_CITY_LON,
            DbContract.CityEntry.COLUMN_CITY_LAT,
    };

    private CitiesLocalDataSource(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public static CitiesLocalDataSource getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new CitiesLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getCities(LoadCitiesCallback callback) {

        List<City> cities = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(DbContract.CityEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                Integer columnId = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_ID));
                Integer cityId = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_NAME));
                Float longitude = cursor.getFloat(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_LON));
                Float latitude = cursor.getFloat(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_LAT));

                City city = new City(columnId, cityId, name, longitude, latitude);
                cities.add(city);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        if (cities.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onCitiesLoaded(cities);
        }
    }


    @Override
    public void getCity(Integer id, GetCityCallback callback) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selection = DbContract.CityEntry.COLUMN_CITY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(DbContract.CityEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);

        City city = null;

        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();

            Integer columnId = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_ID));
            Integer cityId = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_NAME));
            Float longitude = cursor.getFloat(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_LON));
            Float latitude = cursor.getFloat(cursor.getColumnIndexOrThrow(DbContract.CityEntry.COLUMN_CITY_LAT));

            city = new City(columnId, cityId, name, longitude, latitude);

        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        if (cursor != null) {
            callback.onCityLoaded(city);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addCity(City city) {

        BaseUtils.checkNotNull(city);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DbContract.CityEntry.COLUMN_CITY_ID, city.getCityId());
        values.put(DbContract.CityEntry.COLUMN_CITY_NAME, city.getCityName());
        values.put(DbContract.CityEntry.COLUMN_CITY_LON, city.getCityLongitude());
        values.put(DbContract.CityEntry.COLUMN_CITY_LAT, city.getCityLatitude());

        db.insert(DbContract.CityEntry.TABLE_NAME, null, values);
        db.close();

    }

    public void deleteAllCities() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DbContract.CityEntry.TABLE_NAME, null, null);
        db.close();

    }

    @Override
    public void deleteCity(Integer cityId) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = DbContract.CityEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(cityId)};

        db.delete(DbContract.CityEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}

