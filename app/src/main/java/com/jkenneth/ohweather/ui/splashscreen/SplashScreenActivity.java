package com.jkenneth.ohweather.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jkenneth.ohweather.R;
import com.jkenneth.ohweather.ui.weather.WeatherActivity;

/**
 * Displays the logo of the app.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    private SplashScreenContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPresenter = new SplashScreenPresenter(this);

        if (isTaskRoot()) {
            // Start showing splash screen
            mPresenter.start();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.stop();
        super.onDestroy();
    }

    @Override
    public void showMainScreen() {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
        finish();
    }
}
