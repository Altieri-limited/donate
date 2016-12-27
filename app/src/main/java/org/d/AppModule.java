package org.d;

import android.app.Application;
import android.content.Context;

import org.d.model.lycs.Charity;
import org.d.util.AndroidUtil;
import org.d.util.AppCalendar;
import org.d.util.CompatUtil;
import org.d.util.DateUtil;
import org.d.util.ObservableUtil;
import org.d.util.UiUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    AppCalendar providesCalendar() {
        return new AppCalendar();
    }

    @Provides
    @Singleton
    CompatUtil providesCompatUtil() {
        return new CompatUtil(mApplication);
    }

    @Provides
    @Singleton
    ObservableUtil<Integer> providesObservableUtil() {
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
    PublishSubject<Integer> providesPublishSubjectInteger() {
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

    @Provides
    UiUtil providesUiUtil(CompatUtil compatUtil) {
        return new UiUtil(compatUtil);
    }

    @Provides
    DateUtil providesDateUtil(Context context) {
        return new DateUtil(context);
    }

    @Provides
    AndroidUtil providesAndroidUtil(Context context) {
        return new AndroidUtil(context);
    }

}