package com.jkenneth.ohweather.data.source;

import android.support.annotation.NonNull;

import com.jkenneth.ohweather.data.source.remote.WeatherRemoteDataSource;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherRepository implements WeatherDataSource {

    private static WeatherRepository sInstance = null;
    private WeatherRemoteDataSource mWeatherRemoteDataSource;

    private WeatherRepository(@NonNull WeatherRemoteDataSource weatherRemoteDataSource) {
        mWeatherRemoteDataSource = weatherRemoteDataSource;
    }

    public static synchronized WeatherRepository getInstance(
            WeatherRemoteDataSource weatherRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new WeatherRepository(weatherRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void close() {
        mWeatherRemoteDataSource.close();
    }

    @Override
    public void getWeatherList(@NonNull String cityIds, @NonNull GetWeatherCallback callback) {
        mWeatherRemoteDataSource.getWeatherList(cityIds, callback);
    }

    @Override
    public void getWeatherDetailByCityId(long cityId, @NonNull GetWeatherDetailCallback callback) {
        mWeatherRemoteDataSource.getWeatherDetailByCityId(cityId, callback);
    }
}
