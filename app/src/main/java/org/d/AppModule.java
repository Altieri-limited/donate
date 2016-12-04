package org.d;

import android.app.Application;

import org.d.model.lycs.Charity;
import org.d.util.AppCalendar;
import org.d.util.CompatUtil;
import org.d.util.ObservableUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

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

    @Provides
    @Singleton
    CompatUtil providesCompatUtil() {
        return new CompatUtil();
    }

    @Provides
    @Singleton
    ObservableUtil<Charity> providesObservableUtil() {
        return new ObservableUtil<>();
    }

    @Provides
    BehaviorSubject<Double> providesBehaviorSubjectDouble() {
        return BehaviorSubject.create();
    }

    @Provides
    PublishSubject<Double> providesPublishSubjectDouble() {
        return PublishSubject.create();
    }

    @Provides
    BehaviorSubject<Charity> providesBehaviorSubjectCharity() {
        return BehaviorSubject.create();
    }

    @Provides
    PublishSubject<Charity> providesPublishSubjectCharity() {
        return PublishSubject.create();
    }

    @Provides
    @Singleton
    ObservableUtil<Void> providesObservableUtilVoid() {
        return new ObservableUtil<>();
    }

    @Provides
    BehaviorSubject<Void> providesBehaviorSubjectVoid() {
        return BehaviorSubject.create();
    }

}