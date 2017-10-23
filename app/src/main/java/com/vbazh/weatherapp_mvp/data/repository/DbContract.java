package com.vbazh.weatherapp_mvp.data.repository;


import android.provider.BaseColumns;

public final class DbContract {

    public static abstract class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_WEATHER_CITY_ID = "id_city";
        public static final String COLUMN_WEATHER_CITY_NAME = "weather_city_name";
        public static final String COLUMN_WEATHER_CITY_LON = "weather_city_lon";
        public static final String COLUMN_WEATHER_CITY_LAT = "weather_city_lat";
        public static final String COLUMN_WEATHER_TEMP = "weather_temp";
        public static final String COLUMN_WEATHER_PRESSURE = "weather_pressure";
        public static final String COLUMN_WEATHER_HUMIDITY = "weather_humidity";
    }

}
