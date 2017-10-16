package com.vbazh.weatherapp_mvp.data.entities;


public class Weather {

    private Integer id;
    private String temperature;

    public Weather(Integer id, String temperature) {
        this.id = id;
        this.temperature = temperature;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
