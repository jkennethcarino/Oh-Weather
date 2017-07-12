package com.jkenneth.ohweather.ui.weather.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class Weather {

    @SerializedName("cnt")
    private int count;

    @SerializedName("list")
    private List<City> cities;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
