package com.vbazh.weatherapp_mvp.data.repository.weather;


import com.vbazh.weatherapp_mvp.data.entities.Weather;

public interface WeatherDataSource {

    interface GetWeatherCallback {

        void onWeatherLoaded(Weather weather);

        void onDataNotAvailable();

    }

    void getWeather(String name, GetWeatherCallback callback);

}
