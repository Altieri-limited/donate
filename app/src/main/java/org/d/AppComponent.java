package org.d;

import org.d.data.storage.AppStorageModule;
import org.d.network.NetModule;
import org.d.ui.activity.BaseActivity;
import org.d.ui.activity.MainActivity;
import org.d.ui.fragment.SavedMoneyBaseFragment;
import org.d.ui.widget.AmountView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class, AppStorageModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(BaseActivity activity);
    void inject(SavedMoneyBaseFragment fragment);
    void inject(AmountView view);
}
