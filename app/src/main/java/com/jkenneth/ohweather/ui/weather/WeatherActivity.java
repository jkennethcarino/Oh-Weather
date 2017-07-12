package com.jkenneth.ohweather.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jkenneth.ohweather.data.source.WeatherRepository;
import com.jkenneth.ohweather.data.source.remote.WeatherRemoteDataSource;
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

    private WeatherContract.Presenter mPresenter;

    private String[] mCityIds;

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
        mPresenter = new WeatherPresenter(fragment,
                WeatherRepository.getInstance(WeatherRemoteDataSource.getInstance()));

        // Get the list of city IDs (London, Prague, and San Francisco)
        mCityIds = getResources().getStringArray(R.array.city_ids);
        // Show weather information of those cities
        mPresenter.populateWeatherList(mCityIds);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mPresenter.refreshWeatherList(mCityIds);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinder = null;
    }
}
