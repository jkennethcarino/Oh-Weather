package com.jkenneth.ohweather.data.source.remote;

import com.jkenneth.ohweather.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class RestClient {

    private static RestClient sInstance;
    private WeatherService mWeatherService;

    private RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(!BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.NONE
                : HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        Retrofit retrofit = builder.build();
        mWeatherService = retrofit.create(WeatherService.class);
    }

    public static synchronized RestClient getInstance() {
        if (sInstance == null) {
            sInstance = new RestClient();
        }
        return sInstance;
    }

    public WeatherService getWeatherService() {
        return mWeatherService;
    }
}
