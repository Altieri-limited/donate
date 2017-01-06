package org.d.data;

import org.d.AppModule;
import org.d.data.storage.AppStorageModule;
import org.d.network.NetModule;
import org.d.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppStorageModule.class, AppModule.class, NetModule.class, SharedPrefsModule.class})
public interface SharedPrefsComponent {
    void inject(MainActivity mainActivity);
}

