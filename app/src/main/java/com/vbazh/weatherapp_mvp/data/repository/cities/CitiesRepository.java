package com.vbazh.weatherapp_mvp.data.repository.cities;

import com.vbazh.weatherapp_mvp.data.entities.City;
import com.vbazh.weatherapp_mvp.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

public class CitiesRepository implements CityDataSource {

    private static CitiesRepository INSTANCE = null;

    private final CityDataSource mCitiesLocalDataSource;

    public CitiesRepository(CityDataSource mCitiesLocalDataSource) {
        this.mCitiesLocalDataSource = BaseUtils.checkNotNull(mCitiesLocalDataSource);
    }

    public static CitiesRepository getInstance(CityDataSource citiesLocalDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new CitiesRepository(citiesLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getCities(final LoadCitiesCallback callback) {

        BaseUtils.checkNotNull(callback);

        mCitiesLocalDataSource.getCities(new LoadCitiesCallback() {
            @Override
            public void onCitiesLoaded(List<City> cities) {
                callback.onCitiesLoaded(new ArrayList<City>(cities));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getCity(Integer cityId, final GetCityCallback callback) {

        BaseUtils.checkNotNull(cityId);
        BaseUtils.checkNotNull(callback);

        mCitiesLocalDataSource.getCity(cityId, new GetCityCallback() {
            @Override
            public void onCityLoaded(City city) {
                callback.onCityLoaded(city);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    @Override
    public void addCity(City city) {

        BaseUtils.checkNotNull(city);
        mCitiesLocalDataSource.addCity(city);

    }

    @Override
    public void deleteCity(Integer id) {
        mCitiesLocalDataSource.deleteCity(BaseUtils.checkNotNull(id));
    }
}
