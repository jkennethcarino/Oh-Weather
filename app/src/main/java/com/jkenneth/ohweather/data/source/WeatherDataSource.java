package com.jkenneth.ohweather.data.source;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.ui.weather.domain.model.City;
import com.jkenneth.ohweather.ui.weather.domain.model.Weather;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public interface WeatherDataSource {

    interface GetWeatherCallback {

        void onWeatherLoaded(Weather weather);

        void onFailure();
    }

    interface GetWeatherDetailCallback {

        void onWeatherDetailLoaded(City city);

        void onFailure();
    }

    void close();

    void getWeatherList(@NonNull String cityIds, @NonNull GetWeatherCallback callback);

    void getWeatherDetailByCityId(long cityId, @NonNull GetWeatherDetailCallback callback);
}
