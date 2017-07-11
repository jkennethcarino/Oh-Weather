package com.jkenneth.ohweather.ui.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkenneth.ohweather.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Displays a list of weather information. User can choose to view the details.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherFragment extends Fragment implements WeatherContract.View {

    private Unbinder mUnbinder;

    private WeatherContract.Presenter mPresenter;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mUnbinder = ButterKnife.bind(this, view);

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
}
