package com.vbazh.weatherapp_mvp.data.repository.weather;


import android.util.Log;

import com.vbazh.weatherapp_mvp.data.entities.Weather;
import com.vbazh.weatherapp_mvp.utils.BaseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WeatherRepository implements WeatherDataSource {

    private static WeatherRepository INSTANCE = null;

    private final WeatherDataSource mWeatherRemoteDataSource;

    private final WeatherDataSource mWeatherLocalDataSource;

    Map<String, Weather> mCachedWeather;

    boolean mCacheIsDirty = false;

    public WeatherRepository(WeatherDataSource weatherRemoteDataSource,
                             WeatherDataSource weatherLocalDataSource) {

        this.mWeatherRemoteDataSource = BaseUtils.checkNotNull(weatherRemoteDataSource);
        this.mWeatherLocalDataSource = BaseUtils.checkNotNull(weatherLocalDataSource);

    }

    public static WeatherRepository getInstance(WeatherDataSource weatherRemoteDataSource,
                                                WeatherLocalDataSource weatherLocalDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new WeatherRepository(weatherRemoteDataSource, weatherLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {

        INSTANCE = null;

    }

    @Override
    public void getWeather(final Integer id, final GetWeatherCallback callback) {

        BaseUtils.checkNotNull(id);
        BaseUtils.checkNotNull(callback);

        final Weather cachedWeather = getWeatherWithId(String.valueOf(id));

        if (cachedWeather != null) {
            callback.onWeatherLoaded(cachedWeather);
            return;
        }

        mWeatherLocalDataSource.getWeather(id, new GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(Weather weather) {
                if (mCachedWeather == null) {
                    mCachedWeather = new LinkedHashMap<>();
                }
                mCachedWeather.put(weather.getId(), weather);
                callback.onWeatherLoaded(weather);
            }

            @Override
            public void onDataNotAvailable() {

                mWeatherRemoteDataSource.getWeather(cachedWeather.getCityName(), new GetWeatherCallback() {
                    @Override
                    public void onWeatherLoaded(Weather weather) {
                        if (mCachedWeather == null) {
                            mCachedWeather = new LinkedHashMap<>();
                        }
                        mCachedWeather.put(weather.getId(), weather);
                        callback.onWeatherLoaded(weather);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }
        });
    }


    @Override
    public void getWeather(String id, final GetWeatherCallback callback) {

        mWeatherLocalDataSource.getWeather(id, new GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(final Weather weather) {

                mWeatherRemoteDataSource.getWeather(weather.getCityName(), new GetWeatherCallback() {
                    @Override
                    public void onWeatherLoaded(Weather weather) {
                        callback.onWeatherLoaded(weather);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onWeatherLoaded(weather);
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveWeather(final Weather weather, final WeatherSavedCallback callback) {

        BaseUtils.checkNotNull(weather);

        mWeatherRemoteDataSource.getWeather(weather.getCityName(), new GetWeatherCallback() {
            @Override
            public void onWeatherLoaded(Weather loadedWeather) {

                final Weather newWeather = new Weather();
                newWeather.setCityName(loadedWeather.getCityName());
                newWeather.setIdCity(loadedWeather.getIdCity());
                newWeather.setTemperature(loadedWeather.getTemperature());
                newWeather.setCityLon(loadedWeather.getCityLon());
                newWeather.setCityLat(loadedWeather.getCityLat());
                newWeather.setPressure(loadedWeather.getPressure());
                newWeather.setHumidity(loadedWeather.getHumidity());

                newWeather.setId(UUID.randomUUID().toString());
                mWeatherLocalDataSource.saveWeather(newWeather, new WeatherSavedCallback() {
                    @Override
                    public void onWeatherSaved() {
                        if (mCachedWeather == null) {
                            mCachedWeather = new LinkedHashMap<>();
                        }
                        mCachedWeather.put(newWeather.getId(), newWeather);
                        callback.onWeatherSaved();
                    }

                    @Override
                    public void onWeatherSaveFailed() {
                        callback.onWeatherSaveFailed();
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

                final Weather newWeather = new Weather();

                newWeather.setId(UUID.randomUUID().toString());
                newWeather.setCityName(weather.getCityName());
                mWeatherLocalDataSource.saveWeather(newWeather, new WeatherSavedCallback() {
                    @Override
                    public void onWeatherSaved() {
                        if (mCachedWeather == null) {
                            mCachedWeather = new LinkedHashMap<>();
                        }
                        mCachedWeather.put(newWeather.getId(), newWeather);
                        callback.onWeatherSaved();
                    }

                    @Override
                    public void onWeatherSaveFailed() {
                        callback.onWeatherSaveFailed();
                    }
                });
            }
        });
    }

    @Override
    public void getAllWeather(final GetAllWeatherLoaded callback) {

        BaseUtils.checkNotNull(callback);

        if (mCachedWeather != null) {
            callback.onAllWeatherLoaded(new ArrayList<>(mCachedWeather.values()));
            return;
        }

        getAllWeatherFromLocalDataSource(callback);
    }

    @Override
    public void getAllWeather(Map<String, String> ids, GetAllWeatherLoaded callback) {

    }

    @Override
    public void deleteWeather(String id) {

        mWeatherLocalDataSource.deleteWeather(id);
//        mWeatherRemoteDataSource.deleteWeather(id);

        mCachedWeather.remove(id);
    }

    @Override
    public void deleteAllWeather() {

    }

    @Override
    public void refreshWeather() {
        mCacheIsDirty = true;
    }

    private void getAllWeatherFromLocalDataSource(final GetAllWeatherLoaded callback) {

        mWeatherLocalDataSource.getAllWeather(new GetAllWeatherLoaded() {
            @Override
            public void onAllWeatherLoaded(List<Weather> weatherList) {
                refreshCache(weatherList);
                callback.onAllWeatherLoaded(weatherList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Weather> weatherList) {

        if (mCachedWeather == null) {
            mCachedWeather = new LinkedHashMap<>();
        }
        mCachedWeather.clear();
        for (Weather weather : weatherList) {
            mCachedWeather.put(weather.getId(), weather);
        }
        mCacheIsDirty = false;
    }

    private Weather getWeatherWithId(String id) {
        BaseUtils.checkNotNull(id);

        if (mCachedWeather == null || mCachedWeather.isEmpty()) {
            return null;
        } else {
            return mCachedWeather.get(id);
        }
    }
}
