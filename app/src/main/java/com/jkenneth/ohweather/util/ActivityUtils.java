package com.jkenneth.ohweather.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Jhon Kenneth Carino on 7/11/17.
 */

public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * Launches a new activity.
     *
     * @param context A Context of an application or activity.
     * @param cls     The component class that is to be used for the intent.
     */
    public static void startActivity(@NonNull Activity context, @NonNull Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }
}
