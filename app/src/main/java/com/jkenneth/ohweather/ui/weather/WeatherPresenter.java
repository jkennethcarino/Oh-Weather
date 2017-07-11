package com.jkenneth.ohweather.ui.weather;

import android.support.annotation.NonNull;

/**
 * Listens to user actions from the UI ({@link WeatherActivity} and {@link WeatherFragment}),
 * retrieves the data and updates the UI as required.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View mWeatherView;

    public WeatherPresenter(@NonNull WeatherContract.View weatherView) {
        mWeatherView = weatherView;

        mWeatherView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        mWeatherView = null;
    }

    @Override
    public void populateWeatherInfo() {

    }
}
