package org.d;

import org.d.data.storage.AppStorageModule;
import org.d.ui.fragment.SavedMoneyBaseFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, AppStorageModule.class})
public interface AppComponent {
    void inject(SavedMoneyBaseFragment fragment);

}
