package com.vbazh.weatherapp_mvp.screens.citylist;


import com.vbazh.weatherapp_mvp.base.BasePresenter;
import com.vbazh.weatherapp_mvp.base.BaseView;
import com.vbazh.weatherapp_mvp.data.entities.Weather;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRepository;

import java.util.List;

public interface CityListContract {

    interface View extends BaseView {

        void setLoadingIndicator(boolean isShow);

        void showCities(List<Weather> allWeather);

        void showNocities(boolean isVisible);

        void showWeather(String id);

        void showAddWeather();

        void showSuccessfullySavedCity();

        void showSuccessfullyDeleteCity();

    }

    interface Presenter extends BasePresenter<View> {

        void attachView(View view);

        void result(int requestCode, int resultCode);

        void loadCities(boolean forceUpdate);

        void addNewCity();

        void showCityWeather(String id);

        void deleteCity(String cityId);

    }

}
