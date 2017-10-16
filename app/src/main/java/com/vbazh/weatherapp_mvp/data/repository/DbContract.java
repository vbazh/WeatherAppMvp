package com.vbazh.weatherapp_mvp.data.repository;


import android.provider.BaseColumns;

public final class DbContract {

    public static abstract class CityEntry implements BaseColumns {

        public static final String TABLE_NAME = "cities";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_CITY_LON = "city_lon";
        public static final String COLUMN_CITY_LAT = "city_lat";
    }

    public static abstract class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_NAME_CITY_ID = "id";
        //any parameters

    }

}
