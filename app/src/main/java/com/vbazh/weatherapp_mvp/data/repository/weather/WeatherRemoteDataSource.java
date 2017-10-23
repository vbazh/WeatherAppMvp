package com.vbazh.weatherapp_mvp.data.repository.weather;

import com.vbazh.weatherapp_mvp.BuildConfig;
import com.vbazh.weatherapp_mvp.data.entities.Weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRemoteDataSource implements WeatherDataSource {

    private static WeatherRemoteDataSource INSTANCE;

    private WeatherApi weatherApi;

    private WeatherRemoteDataSource() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        weatherApi = retrofit.create(WeatherApi.class);

    }

    public static WeatherRemoteDataSource getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new WeatherRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getWeather(Integer id, final GetWeatherCallback callback) {


    }

    @Override
    public void getWeather(String cityName, final GetWeatherCallback callback) {

        weatherApi.getWeather(cityName, BuildConfig.API_KEY).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, retrofit2.Response<WeatherResponse> response) {
                if (response.isSuccessful()) {

                    Weather weather = new Weather(response.body().getId(),

                            response.body().getName(),
                            response.body().getCoord().getLon(),
                            response.body().getCoord().getLat(),
                            response.body().getMain().getTemp(),
                            response.body().getMain().getPressure(),
                            response.body().getMain().getHumidity());

                    callback.onWeatherLoaded(weather);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void saveWeather(Weather weather, WeatherSavedCallback callback) {

    }

    @Override
    public void getAllWeather(GetAllWeatherLoaded callback) {

    }

    @Override
    public void getAllWeather(Map<String, String> ids, final GetAllWeatherLoaded callback) {

    }

    @Override
    public void deleteWeather(String id) {

    }

    @Override
    public void deleteAllWeather() {

    }

    @Override
    public void refreshWeather() {

    }

}
