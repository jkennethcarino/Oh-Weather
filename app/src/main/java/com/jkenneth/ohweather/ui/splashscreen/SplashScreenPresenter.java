package com.jkenneth.ohweather.ui.splashscreen;

import android.os.Handler;
import android.support.annotation.NonNull;

/**
 * Listens to user actions from the UI ({@link SplashScreenActivity}),
 * retrieves the data and updates the UI as required.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private static final int SPLASH_TIMEOUT_IN_MILLIS = 2000; // 2 seconds

    private SplashScreenContract.View mSplashScreenView;
    private Handler mHandler;

    public SplashScreenPresenter(@NonNull SplashScreenContract.View splashScreenView) {
        mSplashScreenView = splashScreenView;
    }

    @Override
    public void start() {
        if (mHandler == null) {
            mHandler = new Handler();

            // Start the splash screen
            mHandler.postDelayed(mRunnable, SPLASH_TIMEOUT_IN_MILLIS);
        }
    }

    @Override
    public void stop() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
            mHandler = null;
            mSplashScreenView = null;
        }
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSplashScreenView != null) {
                mSplashScreenView.showMainScreen();
            }
        }
    };
}
