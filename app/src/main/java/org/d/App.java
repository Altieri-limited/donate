package org.d;

import android.app.Application;

import org.d.network.DaggerNetComponent;
import org.d.network.NetComponent;
import org.d.network.NetModule;
import org.d.util.log.AppTree;

import io.realm.Realm;
import timber.log.Timber;

public class App extends Application {
    public NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Timber.plant(new AppTree());
        String baseUrlTLYCS = getString(R.string.tlycs_url);
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule(baseUrlTLYCS))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
