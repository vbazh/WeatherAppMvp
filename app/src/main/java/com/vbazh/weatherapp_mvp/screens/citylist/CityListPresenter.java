package com.vbazh.weatherapp_mvp.screens.citylist;

import android.app.Activity;

import com.vbazh.weatherapp_mvp.data.entities.Weather;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherLocalDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRemoteDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRepository;
import com.vbazh.weatherapp_mvp.screens.addcity.AddCityActivity;

import java.util.ArrayList;
import java.util.List;

public class CityListPresenter implements CityListContract.Presenter {

    private CityListContract.View view;
    private WeatherRepository mWeatherRepository;
    private boolean mFirstLoad = true;

    public CityListPresenter(WeatherRepository weatherRepository) {
        this.mWeatherRepository = weatherRepository;
    }

    @Override
    public void attachView(CityListContract.View view) {
        this.view = view;
        updateView();
    }

    private void updateView() {
        if (view != null) {
            loadCities(false);
        }
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddCityActivity.REQUEST_ADD_CITY == requestCode && Activity.RESULT_OK == resultCode) {
            view.showSuccessfullySavedCity();
            updateView();
        }
    }

    @Override
    public void loadCities(boolean forceUpdate) {
        loadCities(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadCities(boolean forceUpdate, final boolean showLoadingUI) {

        if (showLoadingUI) {
            view.setLoadingIndicator(true);
        }

        mWeatherRepository.getAllWeather(new WeatherDataSource.GetAllWeatherLoaded() {

            @Override
            public void onAllWeatherLoaded(List<Weather> weatherList) {

                List<Weather> newWeatherList = new ArrayList<>();
                newWeatherList.addAll(weatherList);
                if (view == null) {
                    return;
                }

                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
                process(newWeatherList);
            }

            @Override
            public void onDataNotAvailable() {
                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
                view.showNocities(true);
            }
        });
    }

    private void process(List<Weather> newWeatherList) {
        if (newWeatherList.isEmpty()) {
            view.showNocities(true);

        } else {
            view.showCities(newWeatherList);
        }
    }

    @Override
    public void addNewCity() {
        view.showAddWeather();
    }

    @Override
    public void showCityWeather(String id) {
        view.showWeather(id);
    }

    @Override
    public void deleteCity(String cityId) {
        mWeatherRepository.deleteWeather(cityId);
        view.showSuccessfullyDeleteCity();
        updateView();
    }
}
