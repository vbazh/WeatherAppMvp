package com.vbazh.weatherapp_mvp.data.repository.weather;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WeatherApi {

    @GET("2.5/weather")
    Call<WeatherResponse> getWeather(@Query("q") String name, @Query("APPID") String apiKey);
}
