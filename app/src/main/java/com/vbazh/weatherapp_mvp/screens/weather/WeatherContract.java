package com.vbazh.weatherapp_mvp.screens.weather;


import com.vbazh.weatherapp_mvp.base.BasePresenter;
import com.vbazh.weatherapp_mvp.base.BaseView;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherLocalDataSource;

public interface WeatherContract {

    interface View extends BaseView {

        void setLoadingIndicator(boolean showLoading);

        void showInfo();

        void setCityName(String cityName);

        void setTemperature(Double temperature);

        void setPressure(Integer pressure);

        void setHumidity(Integer humidity);

        void setTitle(String newTitle);

    }

    interface Presenter extends BasePresenter<View> {

        void attachView(WeatherContract.View view);

        void result(int requestCode, int resultCode);

        void loadWeather(boolean forceUpdate);

        void updateView();

    }
}
