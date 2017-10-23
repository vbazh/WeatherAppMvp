package com.vbazh.weatherapp_mvp.data.entities;


import java.util.Objects;

public class Weather {

    private String id;
    private Integer idCity;
    private String cityName;
    private Double cityLon;
    private Double cityLat;
    private Double temperature;
    private Integer pressure;
    private Integer humidity;


    public Weather() {
    }

    public Weather(String id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }

    public Weather(String id, Integer idCity, String cityName, Double cityLon, Double cityLat, Double temperature, Integer pressure, Integer humidity) {
        this.id = id;
        this.idCity = idCity;
        this.cityName = cityName;
        this.cityLon = cityLon;
        this.cityLat = cityLat;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public Weather(Integer idCity, String cityName, Double cityLon, Double cityLat, Double temperature, Integer pressure, Integer humidity) {
        this.idCity = idCity;
        this.cityName = cityName;
        this.cityLon = cityLon;
        this.cityLat = cityLat;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getCityLon() {
        return cityLon;
    }

    public void setCityLon(Double cityLon) {
        this.cityLon = cityLon;
    }

    public Double getCityLat() {
        return cityLat;
    }

    public void setCityLat(Double cityLat) {
        this.cityLat = cityLat;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(id, weather.id) &&
                Objects.equals(idCity, weather.idCity) &&
                Objects.equals(cityName, weather.cityName) &&
                Objects.equals(cityLon, weather.cityLon) &&
                Objects.equals(cityLat, weather.cityLat) &&
                Objects.equals(temperature, weather.temperature) &&
                Objects.equals(pressure, weather.pressure) &&
                Objects.equals(humidity, weather.humidity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCity, cityName, cityLon, cityLat, temperature, pressure, humidity);
    }
}
