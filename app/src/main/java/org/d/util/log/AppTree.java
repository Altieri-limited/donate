package org.d.util.log;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import org.d.BuildConfig;

import timber.log.Timber;

public class AppTree extends Timber.DebugTree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            super.log(priority, tag, message, t);
        }

        if (priority == Log.ERROR || priority != Log.WARN) {
            FirebaseCrash.log(message);
        }

        if (t != null) {
            FirebaseCrash.report(t);
        }
    }
}
