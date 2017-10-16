package com.vbazh.weatherapp_mvp.data.repository.cities;


import com.vbazh.weatherapp_mvp.data.entities.City;

import java.util.List;

public interface CityDataSource {


    interface LoadCitiesCallback {

        void onCitiesLoaded(List<City> cities);

        void onDataNotAvailable();

    }

    interface GetCityCallback {

        void onCityLoaded(City city);

        void onDataNotAvailable();

    }

    void getCities(LoadCitiesCallback callback);

    void getCity(Integer cityId, GetCityCallback callback);

    void addCity(City city);

    void deleteCity(Integer cityId);

}
