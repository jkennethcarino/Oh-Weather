package com.jkenneth.ohweather.data.source.remote;

import com.jkenneth.ohweather.BuildConfig;
import com.jkenneth.ohweather.ui.weather.domain.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public interface WeatherService {

    /**
     * Returns a list of weather info from the specified {@code ids} of the cities.
     *
     * @param ids     List of city IDs delimited commas
     * @param units   {@code standard}, {@code metric}, and {@code imperial} units are available.
     * @param apiKey  API key
     */
    @GET(BuildConfig.CITY_IDS_API)
    Call<Weather> getWeatherByCityIds(@Query("id") String ids,
                                      @Query("units") String units,
                                      @Query("appid") String apiKey);

}
