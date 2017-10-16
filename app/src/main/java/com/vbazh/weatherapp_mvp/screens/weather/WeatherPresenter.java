package com.vbazh.weatherapp_mvp.screens.weather;

import com.vbazh.weatherapp_mvp.data.entities.Weather;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRemoteDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRepository;

public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View view;

    private WeatherRepository mWeatherRepository;

    private boolean mFirstLoad = true;

    private String cityName = null;

    @Override
    public void attachView(WeatherContract.View view, String city) {
        this.view = view;
        this.cityName = city;
        mWeatherRepository = WeatherRepository.getInstance(WeatherRemoteDataSource.getInstance());
        updateView();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadWeather(boolean forceUpdate) {
        loadWeather(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadWeather(boolean forceUpdate, final boolean showLoadingUI) {

        if (showLoadingUI) {
            view.setLoadingIndicator(true);
        }

        mWeatherRepository.getWeather(cityName, new WeatherDataSource.GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(Weather weather) {
                view.setTitle(cityName);
                view.setCityName(cityName);
                view.setTemperature(weather.getTemperature());
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void updateView() {
        if (view != null) {
            loadWeather(false);
            view.setCityName(cityName);
        }
    }

    @Override
    public void detachView() {
        this.view = null;

    }
}
