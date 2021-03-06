package com.jkenneth.ohweather.ui.weather.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class Coordinate {

    @SerializedName("lon")
    private double longitude;

    @SerializedName("lat")
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
