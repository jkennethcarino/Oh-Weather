package com.jkenneth.ohweather.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jkenneth.ohweather.util.ActivityUtils;
import com.jkenneth.ohweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mUnbinder = ButterKnife.bind(this);

        // Set up the toolbar
        setSupportActionBar(mToolbar);

        WeatherFragment fragment = (WeatherFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_main_content);
        if (fragment == null) {
            // Create fragment
            fragment = WeatherFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.frame_main_content);
        }

        // Create presenter
        WeatherContract.Presenter presenter = new WeatherPresenter(fragment);

        // Show weather information
        presenter.populateWeatherInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinder = null;
    }
}
