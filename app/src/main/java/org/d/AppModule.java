package org.d;

import android.app.Application;

import org.d.util.AppCalendar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    AppCalendar providesCalendar() {
        return new AppCalendar();
    }

}