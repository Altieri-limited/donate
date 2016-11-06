package org.d.network;

import org.d.AppModule;
import org.d.ui.activity.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity mainActivity);
}
