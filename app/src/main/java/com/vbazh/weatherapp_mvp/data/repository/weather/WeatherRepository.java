package com.vbazh.weatherapp_mvp.data.repository.weather;


import com.vbazh.weatherapp_mvp.data.entities.Weather;
import com.vbazh.weatherapp_mvp.utils.BaseUtils;

public class WeatherRepository implements WeatherDataSource {

    private static WeatherRepository INSTANCE = null;

    private final WeatherDataSource mWeatherRemoteDataSource;


    public WeatherRepository(WeatherDataSource weatherRemoteDataSource){
        this.mWeatherRemoteDataSource = BaseUtils.checkNotNull(weatherRemoteDataSource);
    }

    public static WeatherRepository getInstance(WeatherDataSource weatherRemoteDataSource){

        if (INSTANCE == null) {
            INSTANCE = new WeatherRepository(weatherRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getWeather(String name, final GetWeatherCallback callback) {

        BaseUtils.checkNotNull(name);
        BaseUtils.checkNotNull(callback);

        mWeatherRemoteDataSource.getWeather(name, new GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(Weather weather) {
                callback.onWeatherLoaded(weather);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }
}
