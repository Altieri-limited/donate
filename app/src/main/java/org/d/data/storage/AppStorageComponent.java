package org.d.data.storage;

import org.d.AppModule;
import org.d.data.PiggyBank;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, AppStorageModule.class})
public interface AppStorageComponent {
    void inject(PiggyBank piggyBank);
}
