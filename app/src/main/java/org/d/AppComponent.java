package org.d;

import org.d.data.storage.AppStorageModule;
import org.d.network.NetModule;
import org.d.ui.activity.BaseActivity;
import org.d.ui.activity.CharitiesActivity;
import org.d.ui.activity.MainActivity;
import org.d.ui.fragment.CharityDetailsFragment;
import org.d.ui.fragment.CharityImpactFragment;
import org.d.ui.fragment.MoneyAdapter;
import org.d.ui.fragment.MoneyFragment;
import org.d.ui.fragment.SavedMoneyBaseFragment;
import org.d.ui.widget.AmountView;
import org.d.ui.widget.CharitiesHeaderWidget;
import org.d.ui.widget.TitleHeaderWidget;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class, AppStorageModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(BaseActivity activity);
    void inject(CharitiesActivity activity);
    void inject(SavedMoneyBaseFragment fragment);
    void inject(CharityDetailsFragment fragment);
    void inject(CharityImpactFragment fragment);
    void inject(MoneyFragment fragment);

    void inject(AmountView view);
    void inject(CharitiesHeaderWidget view);
    void inject(TitleHeaderWidget widget);

    void inject(MoneyAdapter adapter);
}
