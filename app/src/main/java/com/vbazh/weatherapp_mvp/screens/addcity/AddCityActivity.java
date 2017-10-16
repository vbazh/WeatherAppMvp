package com.vbazh.weatherapp_mvp.screens.addcity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vbazh.weatherapp_mvp.R;
import com.vbazh.weatherapp_mvp.data.repository.cities.CitiesLocalDataSource;


public class AddCityActivity extends AppCompatActivity implements AddCityContract.View {

    public static final int REQUEST_ADD_CITY = 1;

    private AddCityContract.Presenter mPresenter;

    private EditText mTextName;

    private Button saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        init();
        attachPresenter();
    }

    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.text_add_city);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTextName = findViewById(R.id.edit_city);
        saveButton = findViewById(R.id.button_save_city);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveCity(mTextName.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    private void attachPresenter() {
        mPresenter = (AddCityContract.Presenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new AddCityPresenter();
        }
        mPresenter.attachView(this, CitiesLocalDataSource.getInstance(getApplicationContext()));
    }

    @Override
    public void setCityName(String name) {
        mTextName.setText(name);
    }

    @Override
    public void showError() {

    }

    @Override
    public void finishAddCity() {
        setResult(RESULT_OK);
        finish();
    }
}
