package com.jkenneth.ohweather.ui.weatherdetail;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.data.source.WeatherDataSource;
import com.jkenneth.ohweather.data.source.WeatherRepository;
import com.jkenneth.ohweather.ui.weather.domain.model.City;

/**
 * Listens to user actions from the UI ({@link WeatherDetailActivity},
 * retrieves the data and updates the UI as required.
 *
 * Created by Jhon Kenneth Carino on 7/12/17.
 */

public class WeatherDetailPresenter implements WeatherDetailContract.Presenter {

    private WeatherDetailContract.View mWeatherDetailsView;
    private WeatherRepository mWeatherRepository;

    public WeatherDetailPresenter(@NonNull WeatherDetailContract.View weatherDetailsView,
                                  @NonNull WeatherRepository weatherRepository) {
        mWeatherDetailsView = weatherDetailsView;
        mWeatherRepository = weatherRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        mWeatherRepository.close();
        mWeatherDetailsView = null;
    }

    @Override
    public void refreshWeatherDetail(long cityId) {
        if (mWeatherDetailsView != null) {
            mWeatherDetailsView.setLoadingIndicator(true);
        }

        mWeatherRepository.getWeatherDetailByCityId(cityId,
                new WeatherDataSource.GetWeatherDetailCallback() {
                    @Override
                    public void onWeatherDetailLoaded(City city) {
                        if (mWeatherDetailsView != null) {
                            mWeatherDetailsView.setLoadingIndicator(false);
                            mWeatherDetailsView.setWeatherDetail(city);
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (mWeatherDetailsView != null) {
                            mWeatherDetailsView.setLoadingIndicator(false);
                            mWeatherDetailsView.showLoadingWeatherDetailError();
                        }
                    }
                });
    }
}
