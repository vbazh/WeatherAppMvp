package com.vbazh.weatherapp_mvp.screens.citylist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vbazh.weatherapp_mvp.R;
import com.vbazh.weatherapp_mvp.data.entities.City;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesLocalDataSource;
import com.vbazh.weatherapp_mvp.screens.addcity.AddCityActivity;
import com.vbazh.weatherapp_mvp.screens.citylist.ui.CityAdapter;
import com.vbazh.weatherapp_mvp.screens.citylist.ui.RecyclerItemTouchHelper;
import com.vbazh.weatherapp_mvp.screens.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity implements CityListContract.View {

    private CityListContract.Presenter mPresenter;

    private CityAdapter mCityAdapter;

    private TextView mTextNocity;

    private List<City> mCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        init();
        attachPresenter();
    }

    void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.app_name);

        RecyclerView mCityRecycler = findViewById(R.id.recycler_city);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                mPresenter.deleteCity(mCities.get(viewHolder.getAdapterPosition()).getId());
                mCityAdapter.removeItem(viewHolder.getAdapterPosition());
            }
        });

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mCityRecycler);

        mTextNocity = findViewById(R.id.text_no_city);

        mCityAdapter = new CityAdapter(this, mCities, new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String cityName) {
                mPresenter.showCityWeather(cityName);
            }
        });

        mCityRecycler.setLayoutManager(new LinearLayoutManager(this));

        mCityRecycler.setAdapter(mCityAdapter);

        FloatingActionButton fabPlus = findViewById(R.id.fab_plus);

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewCity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void attachPresenter() {
        mPresenter = (CityListContract.Presenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new CityListPresenter();
        }
        mPresenter.attachView(this, CitiesLocalDataSource.getInstance(getApplicationContext()));
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {

    }

    @Override
    public void showCities(List<City> cities) {
        mCities = cities;
        mCityAdapter.replaceData(cities);
        mTextNocity.setVisibility(View.GONE);
    }

    @Override
    public void showNocities(boolean isVisible) {
        if (isVisible) {
            mTextNocity.setVisibility(View.VISIBLE);
        } else mTextNocity.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(String cityName) {
        Intent intent = new Intent(CityListActivity.this, WeatherActivity.class);
        intent.putExtra(WeatherActivity.EXTRA_CITY_NAME, cityName);
        startActivity(intent);
    }

    @Override
    public void showAddWeather() {
        Intent intent = new Intent(CityListActivity.this, AddCityActivity.class);
        startActivityForResult(intent, AddCityActivity.REQUEST_ADD_CITY);
    }

    @Override
    public void showSuccessfullySavedCity() {
        Toast.makeText(this, R.string.text_success_add, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessfullyDeleteCity() {
        Toast.makeText(this, R.string.text_success_delete, Toast.LENGTH_SHORT).show();
    }
}
