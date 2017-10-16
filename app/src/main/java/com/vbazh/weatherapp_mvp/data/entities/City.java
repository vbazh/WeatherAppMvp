package com.vbazh.weatherapp_mvp.data.entities;


public class City {

    private Integer id;
    private Integer cityId;
    private String cityName;
    private Float cityLongitude;
    private Float cityLatitude;

    public City(Integer id, Integer cityId, String cityName, Float cityLongitude, Float cityLatitude) {
        this.id = id;
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityLongitude = cityLongitude;
        this.cityLatitude = cityLatitude;
    }

    public City(String cityName) {
        this.cityName = cityName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Float getCityLongitude() {
        return cityLongitude;
    }

    public void setCityLongitude(Float cityLongitude) {
        this.cityLongitude = cityLongitude;
    }

    public Float getCityLatitude() {
        return cityLatitude;
    }

    public void setCityLatitude(Float cityLatitude) {
        this.cityLatitude = cityLatitude;
    }
}
