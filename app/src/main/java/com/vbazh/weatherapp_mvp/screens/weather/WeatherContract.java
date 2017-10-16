package com.vbazh.weatherapp_mvp.screens.weather;


import com.vbazh.weatherapp_mvp.base.BasePresenter;
import com.vbazh.weatherapp_mvp.base.BaseView;

public interface WeatherContract {

    interface View extends BaseView {

        void setLoadingIndicator(boolean showLoading);

        void showInfo();

        void setCityName(String cityName);

        void setTemperature(String temperature);

        void setTitle(String newTitle);

    }

    interface Presenter extends BasePresenter<View> {

        void attachView(WeatherContract.View view, String city);

        void result(int requestCode, int resultCode);

        void loadWeather(boolean forceUpdate);

    }
}
