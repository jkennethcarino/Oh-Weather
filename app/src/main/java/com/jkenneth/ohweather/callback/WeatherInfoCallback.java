package com.jkenneth.ohweather.callback;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.ui.weather.domain.model.City;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public interface WeatherInfoCallback {

    void onItemClick(@NonNull City city);
}
