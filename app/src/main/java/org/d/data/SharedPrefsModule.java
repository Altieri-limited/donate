package org.d.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModule {
    @NonNull
    @Provides
    @Singleton
    public SharedPrefs provideSharedPrefs(Application app) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        return new SharedPrefs(sharedPreferences);
    }

}
