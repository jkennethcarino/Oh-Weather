package com.jkenneth.ohweather.ui.weather.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class Wind {

    private double speed;

    @SerializedName("deg")
    private double degrees;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }
}
