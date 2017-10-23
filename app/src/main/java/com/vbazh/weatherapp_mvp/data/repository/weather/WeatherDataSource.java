package com.vbazh.weatherapp_mvp.data.repository.weather;


import com.vbazh.weatherapp_mvp.data.entities.Weather;

import java.util.List;
import java.util.Map;

public interface WeatherDataSource {

    interface GetWeatherCallback {

        void onWeatherLoaded(Weather weather);

        void onDataNotAvailable();

    }

    interface GetAllWeatherLoaded {

        void onAllWeatherLoaded(List<Weather> weatherList);

        void onDataNotAvailable();

    }

    interface WeatherSavedCallback {

        void onWeatherSaved();

        void onWeatherSaveFailed();

    }

    void getWeather(Integer id, GetWeatherCallback callback);

    void getWeather(String cityName, GetWeatherCallback callback);

    void saveWeather(Weather weather, WeatherSavedCallback callback);

    void getAllWeather(GetAllWeatherLoaded callback);

    void getAllWeather(Map<String, String> ids, GetAllWeatherLoaded callback);

    void deleteWeather(String id);

    void deleteAllWeather();

    void refreshWeather();

}
