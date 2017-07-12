package com.jkenneth.ohweather.data.source.remote;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.BuildConfig;
import com.jkenneth.ohweather.data.source.WeatherDataSource;
import com.jkenneth.ohweather.ui.weather.domain.model.City;
import com.jkenneth.ohweather.ui.weather.domain.model.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherRemoteDataSource implements WeatherDataSource {

    private static final String DEFAULT_UNIT = "metric";

    private static WeatherRemoteDataSource sInstance;
    private WeatherService mWeatherService;
    private Call<Weather> mWeatherCall;
    private Call<City> mWeatherDetailCall;

    private WeatherRemoteDataSource() {
        mWeatherService = RestClient.getInstance().getWeatherService();
    }

    public static synchronized WeatherRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void close() {
        if (mWeatherCall != null) {
            mWeatherCall.cancel();
            mWeatherCall = null;
        }
        if (mWeatherDetailCall != null) {
            mWeatherDetailCall.cancel();
            mWeatherDetailCall = null;
        }
    }

    @Override
    public void getWeatherList(@NonNull String cityIds, @NonNull final GetWeatherCallback callback) {
        if (mWeatherCall != null) {
            // Cancel the previous call
            mWeatherCall.cancel();
        }

        mWeatherCall =
                mWeatherService.getWeatherByCityIds(cityIds, DEFAULT_UNIT, BuildConfig.API_KEY);
        mWeatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NonNull Call<Weather> call,
                                   @NonNull Response<Weather> response) {
                if (response.isSuccessful()) {
                    Weather data = response.body();
                    callback.onWeatherLoaded(data);
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getWeatherDetailByCityId(long cityId,
                                         @NonNull final GetWeatherDetailCallback callback) {
        if (mWeatherDetailCall != null) {
            mWeatherDetailCall.cancel();
        }

        mWeatherDetailCall = mWeatherService.getWeatherDetailByCityId(cityId, BuildConfig.API_KEY);
        mWeatherDetailCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()) {
                    City city = response.body();
                    callback.onWeatherDetailLoaded(city);
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
