package com.jkenneth.ohweather.ui.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkenneth.ohweather.R;
import com.jkenneth.ohweather.callback.WeatherInfoCallback;
import com.jkenneth.ohweather.ui.weather.domain.model.City;
import com.jkenneth.ohweather.ui.weather.domain.model.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Displays a list of weather information. User can choose to view the details.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherFragment extends Fragment implements WeatherContract.View, WeatherInfoCallback {

    @BindView(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rvWeather) EmptyRecyclerView rvWeather;
    @BindView(android.R.id.empty) TextView tvEmptyView;

    private Unbinder mUnbinder;

    private WeatherContract.Presenter mPresenter;

    private WeatherAdapter mAdapter;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        // Set up SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);

        mAdapter = new WeatherAdapter(this);

        // Set up RecyclerView
        rvWeather.addItemDecoration(new DividerItemDecoration(
                rvWeather.getContext(), DividerItemDecoration.VERTICAL));
        rvWeather.setLayoutManager(new LinearLayoutManager(rvWeather.getContext()));
        rvWeather.setHasFixedSize(true);
        rvWeather.setAdapter(mAdapter);
        rvWeather.setEmptyView(tvEmptyView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mUnbinder = null;
    }

    @Override
    public void setPresenter(@NonNull WeatherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showWeatherList(Weather weather) {
        mAdapter.setWeatherList(weather);
    }

    @Override
    public void showLoadingWeatherListError() {
        if (getView() == null) {
            return;
        }
        Snackbar.make(getView(), R.string.unable_weather_updates,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(@NonNull City city) {
        // TODO: Show weather details of the selected city
    }
}
