package com.vbazh.weatherapp_mvp.screens.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.vbazh.weatherapp_mvp.R;


public class WeatherActivity extends AppCompatActivity implements WeatherContract.View {

    public static final String EXTRA_CITY_NAME = "extra_city_name";
    private TextView cityName;
    private TextView cityTemp;
    String city = null;

    private WeatherContract.Presenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        city = getIntent().getStringExtra(EXTRA_CITY_NAME);
        init();
        attachPresenter();

    }

    private void attachPresenter() {

        mPresenter = (WeatherContract.Presenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new WeatherPresenter();
        }
        mPresenter.attachView(this, city);
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cityName = findViewById(R.id.text_city_name);
        cityTemp = findViewById(R.id.text_city_temp);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean showLoading) {

    }

    @Override
    public void showInfo() {

    }

    @Override
    public void setCityName(String name) {
        cityName.setText(name);
    }

    @Override
    public void setTemperature(String temperature) {
        cityTemp.setText(temperature);
    }

    @Override
    public void setTitle(String newTitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(newTitle);
        }
    }
}
