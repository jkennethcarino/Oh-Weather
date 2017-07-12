package com.jkenneth.ohweather.ui.weatherdetail;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.ui.BasePresenter;
import com.jkenneth.ohweather.ui.weather.domain.model.City;

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by Jhon Kenneth Carino on 7/12/17.
 */

public interface WeatherDetailContract {

    interface View {

        void setLoadingIndicator(boolean active);

        void setWeatherDetail(@NonNull City city);

        void showLoadingWeatherDetailError();
    }

    interface Presenter extends BasePresenter {

        void refreshWeatherDetail(long cityId);
    }
}
