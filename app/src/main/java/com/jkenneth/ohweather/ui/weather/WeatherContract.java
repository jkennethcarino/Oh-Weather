package com.jkenneth.ohweather.ui.weather;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.ui.BasePresenter;

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public interface WeatherContract {

    interface View {

        void setPresenter(@NonNull WeatherContract.Presenter presenter);
    }

    interface Presenter extends BasePresenter {

        void populateWeatherInfo();
    }
}
