package com.jkenneth.ohweather.ui.splashscreen;

import com.jkenneth.ohweather.ui.BasePresenter;

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public interface SplashScreenContract {

    interface View {

        void showMainScreen();
    }

    interface Presenter extends BasePresenter {

    }
}
