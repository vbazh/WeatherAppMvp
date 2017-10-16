package com.vbazh.weatherapp_mvp.screens.addcity;

import com.vbazh.weatherapp_mvp.base.BasePresenter;
import com.vbazh.weatherapp_mvp.base.BaseView;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesLocalDataSource;

public interface AddCityContract {

    interface View extends BaseView {

        void setCityName(String name);

        void showError();

        void finishAddCity();


    }

    interface Presenter extends BasePresenter<View> {

        void attachView(AddCityContract.View view, CitiesLocalDataSource localDataSource);

        void saveCity(String cityName);

    }

}
