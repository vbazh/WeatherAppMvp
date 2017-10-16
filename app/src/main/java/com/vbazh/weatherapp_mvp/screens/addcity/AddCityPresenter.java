package com.vbazh.weatherapp_mvp.screens.addcity;


import android.util.Log;

import com.vbazh.weatherapp_mvp.data.entities.City;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesLocalDataSource;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesRepository;

import java.util.Random;

public class AddCityPresenter implements AddCityContract.Presenter {

    private AddCityContract.View view;
    private CitiesRepository mCitiesRepository;

    @Override
    public void attachView(AddCityContract.View view, CitiesLocalDataSource localDataSource) {
        this.view = view;
        this.mCitiesRepository = CitiesRepository.getInstance(localDataSource);
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void saveCity(String cityName) {
        Log.d("TAG", "savecity name is " + cityName);
        City newCity = new City(cityName);
        mCitiesRepository.addCity(newCity);
        view.finishAddCity();
    }
}
