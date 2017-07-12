package com.jkenneth.ohweather.ui.weather;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jkenneth.ohweather.data.source.WeatherDataSource;
import com.jkenneth.ohweather.data.source.WeatherRepository;
import com.jkenneth.ohweather.ui.weather.domain.model.Weather;

/**
 * Listens to user actions from the UI ({@link WeatherActivity} and {@link WeatherFragment}),
 * retrieves the data and updates the UI as required.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View mWeatherView;
    private WeatherRepository mWeatherRepository;

    public WeatherPresenter(@NonNull WeatherContract.View weatherView,
                            @NonNull WeatherRepository weatherRepository) {
        mWeatherView = weatherView;
        mWeatherRepository = weatherRepository;

        mWeatherView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        mWeatherRepository.close();
        mWeatherView = null;
    }

    @Override
    public void populateWeatherList(String[] cityIds) {
        if (mWeatherRepository != null) {
            mWeatherView.setLoadingIndicator(true);
        }

        // Join the list of city ids delimited by comma
        String cityIdsJoined = TextUtils.join(",", cityIds);

        mWeatherRepository.getWeatherList(cityIdsJoined,
                new WeatherDataSource.GetWeatherCallback() {
                    @Override
                    public void onWeatherLoaded(Weather weather) {
                        if (mWeatherView != null) {
                            mWeatherView.showWeatherList(weather);
                            mWeatherView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (mWeatherView != null) {
                            mWeatherView.setLoadingIndicator(false);
                            mWeatherView.showLoadingWeatherListError();
                        }
                    }
                });
    }

    @Override
    public void refreshWeatherList(String[] cityIds) {
        populateWeatherList(cityIds);
    }
}
