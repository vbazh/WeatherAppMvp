package com.vbazh.weatherapp_mvp.screens.addcity;

import com.vbazh.weatherapp_mvp.data.entities.Weather;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRepository;

public class AddCityPresenter implements AddCityContract.Presenter {

    private AddCityContract.View view;
    private WeatherRepository mWeatherRepository;

    public AddCityPresenter(WeatherRepository weatherRepository) {
        this.mWeatherRepository = weatherRepository;
    }

    @Override
    public void attachView(AddCityContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void saveCity(String cityName) {
        Weather newWeather = new Weather();
        newWeather.setCityName(cityName);
        mWeatherRepository.saveWeather(newWeather, new WeatherDataSource.WeatherSavedCallback() {
            @Override
            public void onWeatherSaved() {
                view.finishAddCity();
            }

            @Override
            public void onWeatherSaveFailed() {
                view.showError();
            }
        });
    }
}
