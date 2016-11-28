package org.d.data;

import org.d.data.storage.AppStorage;
import org.d.network.TheLifeYouCanSaveService;
import org.d.util.AppCalendar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    public PiggyBank providePiggyBank(AppData appData, AppCalendar calendar) {
        return new PiggyBank(appData, calendar);
    }

    @Provides
    @Singleton
    public AppData provideData(AppStorage appStorage, TheLifeYouCanSaveService serviceTLYCS) {
        return new AppData(appStorage, serviceTLYCS);
    }

}
