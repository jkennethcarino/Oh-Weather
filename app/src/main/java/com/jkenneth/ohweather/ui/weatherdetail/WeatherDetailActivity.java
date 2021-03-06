package com.jkenneth.ohweather.ui.weatherdetail;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jkenneth.ohweather.R;
import com.jkenneth.ohweather.data.source.WeatherRepository;
import com.jkenneth.ohweather.data.source.remote.WeatherRemoteDataSource;
import com.jkenneth.ohweather.ui.weather.domain.model.City;
import com.jkenneth.ohweather.ui.weather.domain.model.WeatherInfo;
import com.jkenneth.ohweather.util.AndroidUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Displays weather information of the selected city.
 *
 * Created by Jhon Kenneth Carino on 7/12/17.
 */

public class WeatherDetailActivity extends AppCompatActivity
        implements WeatherDetailContract.View, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.rootView) CoordinatorLayout mRootView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.ivWeatherIcon) ImageView ivWeatherIcon;
    @BindView(R.id.tvTemperature) TextView tvTemperature;
    @BindView(R.id.tvTemperatureMin) TextView tvTemperatureMin;
    @BindView(R.id.tvTemperatureMax) TextView tvTemperatureMax;
    @BindView(R.id.tvWind) TextView tvWind;
    @BindView(R.id.tvCloudiness) TextView tvCloudiness;
    @BindView(R.id.tvPressure) TextView tvPressure;
    @BindView(R.id.tvHumidity) TextView tvHumidity;
    @BindView(R.id.tvGeoCoordinates) TextView tvGeoCoordinates;

    public static final String EXTRA_CITY_JSON = "city_json";
    private static final String FILE_MIME_TYPE = "image/png";
    private static final int RC_WRITE_EXTERNAL_STORAGE = 0x01;

    private Unbinder mUnbinder;

    private DownloadManager mDownloadManager;
    private City mCity;
    private WeatherDetailContract.Presenter mPresenter;

    private long mLastDownloadId = -1L;

    public static Intent getStartIntent(@NonNull Context context, @NonNull City city) {
        Intent intent = new Intent(context, WeatherDetailActivity.class);
        intent.putExtra(EXTRA_CITY_JSON, new Gson().toJson(city));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        mDownloadManager = (DownloadManager) getSystemService(Activity.DOWNLOAD_SERVICE);
        mUnbinder = ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        String cityJson = extras.getString(EXTRA_CITY_JSON);
        mCity = new Gson().fromJson(cityJson, City.class);

        // Set up the toolbar
        mToolbar.setTitle(getString(R.string.format_location,
                mCity.getName(), mCity.getSys().getCountry()));
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Show weather details
        updateUi(mCity);

        // Set up SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setEnabled(false);

        // Create presenter
        mPresenter = new WeatherDetailPresenter(this,
                WeatherRepository.getInstance(WeatherRemoteDataSource.getInstance()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_download:
                requestPermissionAndDownloadWeatherIcon();
                return true;
            case R.id.action_refresh:
                mPresenter.refreshWeatherDetail(mCity.getId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mOnDownloadCompleteReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mOnDownloadCompleteReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
        mUnbinder.unbind();
        mUnbinder = null;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void setWeatherDetail(@NonNull City city) {
        updateUi(city);
    }

    @Override
    public void showLoadingWeatherDetailError() {
        Snackbar.make(mRootView, R.string.unable_weather_detail, Snackbar.LENGTH_LONG).show();
    }

    private void updateUi(@NonNull City city) {
        List<WeatherInfo> weather = city.getWeather();
        if (weather != null && weather.size() > 0) {
            WeatherInfo weatherInfo = weather.get(0);
            // Weather icon
            Glide.with(this)
                    .load(weatherInfo.getIconUrl())
                    .centerCrop()
                    .into(ivWeatherIcon);
            // Cloudiness
            tvCloudiness.setText(getString(R.string.format_cloudiness,
                    weatherInfo.getDescription()));
        }
        // Temperature
        tvTemperature.setText(getString(R.string.format_temperature,
                String.valueOf(city.getMain().getTemperature())));
        tvTemperatureMin.setText(getString(R.string.format_temperature_min,
                String.valueOf(city.getMain().getTemperatureMin())));
        tvTemperatureMax.setText(getString(R.string.format_temperature_max,
                String.valueOf(city.getMain().getTemperatureMax())));
        // Wind
        tvWind.setText(getString(R.string.format_wind,
                String.valueOf(city.getWind().getSpeed()),
                String.valueOf(city.getWind().getDegrees())));
        // Pressure
        tvPressure.setText(getString(R.string.format_pressure,
                String.valueOf(city.getMain().getPressure())));
        // Humidity
        tvHumidity.setText(getString(R.string.format_humidity,
                String.valueOf(city.getMain().getHumidity())));
        // Geo Coordinates
        tvGeoCoordinates.setText(getString(R.string.format_geo_coords,
                city.getCoordinates().getLongitude(),
                city.getCoordinates().getLatitude()));
    }

    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE)
    private void requestPermissionAndDownloadWeatherIcon() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            List<WeatherInfo> weather = mCity.getWeather();
            if (weather != null && weather.size() > 0) {
                WeatherInfo weatherInfo = weather.get(0);
                Uri iconUri = Uri.parse(weatherInfo.getIconUrl());

                DownloadManager.Request request = new DownloadManager.Request(iconUri);
                request.setMimeType(FILE_MIME_TYPE);
                // show notification after download completion
                request.setNotificationVisibility(
                        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                // set file destination
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        iconUri.getLastPathSegment());
                request.allowScanningByMediaScanner();

                // start downloading the icon
                mLastDownloadId = mDownloadManager.enqueue(request);
            }
        } else {
            // Do not have permission, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_write_external),
                    RC_WRITE_EXTERNAL_STORAGE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            Snackbar.make(mRootView, R.string.permission_denied_message,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * This broadcast receiver will be called after the download completion,
     * and tries to open the downloaded weather icon if available.
     */
    private final BroadcastReceiver mOnDownloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mLastDownloadId != -1L) {
                AndroidUtils.openDownloadedFile(WeatherDetailActivity.this,
                        mDownloadManager, mLastDownloadId);
            }
        }
    };
}
