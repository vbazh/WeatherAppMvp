package com.vbazh.weatherapp_mvp.screens.weather;

import com.vbazh.weatherapp_mvp.data.entities.Weather;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherLocalDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRemoteDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRepository;

public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View view;

    private WeatherRepository mWeatherRepository;

    private boolean mFirstLoad = true;

    private String id = null;

    public WeatherPresenter(String id, WeatherRepository weatherRepository) {
        this.id = id;
        this.mWeatherRepository = weatherRepository;
    }

    @Override
    public void attachView(WeatherContract.View view) {
        this.view = view;
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

        mWeatherRepository.getWeather(id, new WeatherDataSource.GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(Weather weather) {
                view.setTitle(weather.getCityName());
                view.setCityName(weather.getCityName());
                view.setTemperature(weather.getTemperature());
                view.setPressure(weather.getPressure());
                view.setHumidity(weather.getHumidity());
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void updateView() {
        if (view != null) {
            loadWeather(false);
        }
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
