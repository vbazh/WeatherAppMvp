package com.vbazh.weatherapp_mvp.data.repository.weather;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("2.5/weather")
    Call<WeatherResponse> getWeather(@Query("q") String name, @Query("APPID") String apiKey);

}
