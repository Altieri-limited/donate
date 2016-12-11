package org.d.data;

import org.d.AppModule;
import org.d.data.storage.AppStorageModule;
import org.d.network.NetModule;
import org.d.ui.activity.MainActivity;
import org.d.ui.activity.SectionsPagerAdapter;
import org.d.ui.fragment.CharitiesFragment;
import org.d.ui.fragment.CharityDetailsFragment;
import org.d.ui.fragment.CharityImpactFragment;
import org.d.ui.fragment.SavedMoneyBaseFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, DataModule.class, NetModule.class, AppStorageModule.class})
public interface DataComponent {
    void inject(MainActivity activity);
    void inject(SavedMoneyBaseFragment fragment);
    void inject(CharitiesFragment fragment);
    void inject(CharityImpactFragment fragment);
    void inject(CharityDetailsFragment fragment);

    void inject(SectionsPagerAdapter adapter);

}
