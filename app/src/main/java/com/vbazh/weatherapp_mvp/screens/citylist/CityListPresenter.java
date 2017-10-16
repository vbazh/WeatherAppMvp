package com.vbazh.weatherapp_mvp.screens.citylist;

import android.app.Activity;

import com.vbazh.weatherapp_mvp.data.entities.City;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesLocalDataSource;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesRepository;
import com.vbazh.weatherapp_mvp.data.repository.cities.CityDataSource;
import com.vbazh.weatherapp_mvp.screens.addcity.AddCityActivity;

import java.util.ArrayList;
import java.util.List;

public class CityListPresenter implements CityListContract.Presenter {

    private CityListContract.View view;
    private CitiesRepository mCitiesRepository;
    private boolean mFirstLoad = true;

    @Override
    public void attachView(CityListContract.View view, CitiesLocalDataSource citiesLocalDataSource) {
        this.view = view;
        mCitiesRepository = CitiesRepository.getInstance(citiesLocalDataSource);
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
            updateView();
            view.showSuccessfullySavedCity();
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

        mCitiesRepository.getCities(new CityDataSource.LoadCitiesCallback() {
            @Override
            public void onCitiesLoaded(List<City> cities) {
                List<City> newCities = new ArrayList<>();
                newCities.addAll(cities);
                if (view == null) {
                    return;
                }
                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
                process(newCities);
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

    private void process(List<City> newCities) {
        if (newCities.isEmpty()) {
            view.showNocities(true);

        } else {
            view.showCities(newCities);
        }
    }

    @Override
    public void addNewCity() {
        view.showAddWeather();
    }

    @Override
    public void showCityWeather(String cityName) {
        view.showWeather(cityName);
    }

    @Override
    public void deleteCity(Integer cityId) {
        mCitiesRepository.deleteCity(cityId);
        view.showSuccessfullyDeleteCity();
    }
}
