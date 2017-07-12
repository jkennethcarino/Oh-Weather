package com.jkenneth.ohweather.ui.weather.domain.model;

import com.google.gson.annotations.SerializedName;
import com.jkenneth.ohweather.BuildConfig;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherInfo {

    private int id;

    @SerializedName("icon")
    private String iconUrl;

    private String main;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconUrl() {
        return String.format(BuildConfig.WEATHER_ICON_URL, iconUrl);
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
