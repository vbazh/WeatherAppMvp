package com.vbazh.weatherapp_mvp.screens.citylist;


import com.vbazh.weatherapp_mvp.base.BasePresenter;
import com.vbazh.weatherapp_mvp.base.BaseView;
import com.vbazh.weatherapp_mvp.data.entities.City;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesLocalDataSource;

import java.util.List;

public interface CityListContract {

    interface View extends BaseView {

        void setLoadingIndicator(boolean isShow);

        void showCities(List<City> cities);

        void showNocities(boolean isVisible);

        void showWeather(String cityName);

        void showAddWeather();

        void showSuccessfullySavedCity();

        void showSuccessfullyDeleteCity();

    }

    interface Presenter extends BasePresenter<View> {

        void attachView(View view, CitiesLocalDataSource localDataSource);

        void result(int requestCode, int resultCode);

        void loadCities(boolean forceUpdate);

        void addNewCity();

        void showCityWeather(String cityName);

        void deleteCity(Integer cityId);

    }

}
