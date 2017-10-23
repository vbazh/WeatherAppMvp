package com.vbazh.weatherapp_mvp.screens.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.vbazh.weatherapp_mvp.R;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherLocalDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRemoteDataSource;
import com.vbazh.weatherapp_mvp.data.repository.weather.WeatherRepository;


public class WeatherActivity extends AppCompatActivity implements WeatherContract.View {

    public static final String EXTRA_ID_CITY = "extra_id_city";
    private TextView cityName;
    private TextView cityTemp;
    private TextView cityPressure;
    private TextView cityHumidity;
    String id = null;

    private WeatherContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        id = getIntent().getStringExtra(EXTRA_ID_CITY);
        init();
        attachPresenter();

    }

    private void attachPresenter() {
        mPresenter = (WeatherContract.Presenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            WeatherRepository weatherRepository = WeatherRepository.getInstance(WeatherRemoteDataSource.getInstance(),
                    WeatherLocalDataSource.getInstance(getApplicationContext()));
            mPresenter = new WeatherPresenter(id, weatherRepository);
        }
        mPresenter.attachView(this);
        mPresenter.updateView();
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
        cityPressure = findViewById(R.id.text_city_pressure);
        cityHumidity = findViewById(R.id.text_city_humidity);
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
    public void setTemperature(Double temperature) {
        cityTemp.setText(String.valueOf(temperature));
    }

    @Override
    public void setPressure(Integer pressure) {
        cityPressure.setText(String.valueOf(pressure));

    }

    @Override
    public void setHumidity(Integer humidity) {
        cityHumidity.setText(String.valueOf(humidity));
    }

    @Override
    public void setTitle(String newTitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(newTitle);
        }
    }
}
