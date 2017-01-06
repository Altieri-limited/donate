package org.d.network;

import org.d.AppModule;
import org.d.data.SharedPrefsModule;
import org.d.data.storage.AppStorageModule;
import org.d.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppStorageModule.class, AppModule.class, SharedPrefsModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity mainActivity);
}
