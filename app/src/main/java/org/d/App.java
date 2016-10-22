package org.d;

import android.app.Application;
import android.content.Context;

import org.util.log.AppTree;

import timber.log.Timber;

public class App extends Application {
    private static Context sInstance;

    public static Context getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Timber.plant(new AppTree());
    }
}
