package com.jkenneth.ohweather.ui.weather;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.ui.BasePresenter;
import com.jkenneth.ohweather.ui.weather.domain.model.Weather;

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public interface WeatherContract {

    interface View {

        void setPresenter(@NonNull WeatherContract.Presenter presenter);

        void setLoadingIndicator(boolean active);

        void showWeatherList(Weather weather);

        void showLoadingWeatherListError();
    }

    interface Presenter extends BasePresenter {

        void populateWeatherList(String[] cityIds);

        void refreshWeatherList(String[] cityIds);
    }
}
