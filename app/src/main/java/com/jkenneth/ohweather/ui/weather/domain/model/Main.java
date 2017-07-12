package com.jkenneth.ohweather.ui.weather.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class Main {

    @SerializedName("temp")
    private double temperature;

    @SerializedName("temp_min")
    private double temperatureMin;

    @SerializedName("temp_max")
    private double temperatureMax;

    private double pressure;
    private int humidity;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
