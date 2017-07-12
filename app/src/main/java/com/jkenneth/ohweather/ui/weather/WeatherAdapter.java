package com.jkenneth.ohweather.ui.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkenneth.ohweather.R;
import com.jkenneth.ohweather.callback.WeatherInfoCallback;
import com.jkenneth.ohweather.ui.weather.domain.model.City;
import com.jkenneth.ohweather.ui.weather.domain.model.Weather;
import com.jkenneth.ohweather.ui.weather.domain.model.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<City> mCities;
    private WeatherInfoCallback mCallback;

    public WeatherAdapter(@NonNull WeatherInfoCallback callback) {
        mCallback = callback;
    }

    public void setWeatherList(final Weather weather) {
        if (mCities == null) {
            if (weather != null && weather.getCities() != null) {
                mCities = weather.getCities();
                notifyItemRangeInserted(0, weather.getCities().size());
            }
        } else {
            if (weather == null || weather.getCities() == null) {
                mCities = new ArrayList<>();
                notifyItemRangeInserted(0, mCities.size());
                return;
            }

            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCities.size();
                }

                @Override
                public int getNewListSize() {
                    return weather.getCities().size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    City old = mCities.get(oldItemPosition);
                    City city = weather.getCities().get(newItemPosition);
                    return old.getId() == city.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    City old = mCities.get(oldItemPosition);
                    City city = weather.getCities().get(newItemPosition);
                    return old.getId() == city.getId()
                            && old.getName().equals(city.getName());
                }
            });
            mCities = weather.getCities();
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_info, parent, false);
        return new ViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mCities.get(position));
    }

    @Override
    public int getItemCount() {
        return mCities == null ? 0 : mCities.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCityName) TextView tvCityName;
        @BindView(R.id.tvActualWeather) TextView tvActualWeather;
        @BindView(R.id.tvTemperature) TextView tvTemperature;

        private final View mRootView;
        private final WeatherInfoCallback mCallback;

        public ViewHolder(View view, final WeatherInfoCallback callback) {
            super(view);
            ButterKnife.bind(this, view);
            mRootView = view;
            mCallback = callback;
        }

        void bind(@NonNull final City city) {
            Context context = mRootView.getContext();

            List<WeatherInfo> weatherInfoList = city.getWeather();
            WeatherInfo weather = null;
            if (weatherInfoList != null && weatherInfoList.size() > 0) {
                // Get the first weather info
                weather = weatherInfoList.get(0);
            }

            // Location
            tvCityName.setText(context.getString(R.string.format_location,
                    city.getName(), city.getSys().getCountry()));

            // Actual Weather
            tvActualWeather.setText(weather == null ?
                    context.getString(R.string.no_weather_desc)
                    : context.getString(R.string.format_actual_weather,
                    weather.getMain(), weather.getDescription()));

            // Temperature
            tvTemperature.setText(context.getString(R.string.format_temperature,
                    String.valueOf(city.getMain().getTemperature())));

            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onItemClick(city);
                }
            });
        }
    }
}
