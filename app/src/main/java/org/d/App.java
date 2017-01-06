package org.d;

import android.app.Application;

import org.d.data.DaggerDataComponent;
import org.d.data.DaggerSharedPrefsComponent;
import org.d.data.DataComponent;
import org.d.data.DataModule;
import org.d.data.SharedPrefsComponent;
import org.d.data.SharedPrefsModule;
import org.d.data.storage.AppStorageComponent;
import org.d.data.storage.AppStorageModule;
import org.d.data.storage.DaggerAppStorageComponent;
import org.d.network.DaggerNetComponent;
import org.d.network.NetComponent;
import org.d.network.NetModule;
import org.d.util.log.AppTree;

import io.realm.Realm;
import timber.log.Timber;

public class App extends Application {
    public NetComponent mNetComponent;
    public SharedPrefsComponent mSharedPrefsComponent;
    public AppStorageComponent mAppStorageComponent;
    private DataComponent mDataComponent;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Timber.plant(new AppTree());
        String baseUrlTLYCS = getString(R.string.tlycs_url);
        NetModule netModule = new NetModule(baseUrlTLYCS);
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .netModule(netModule)
                .appModule(new AppModule(this))
                .build();

        mSharedPrefsComponent = DaggerSharedPrefsComponent.builder()
                .sharedPrefsModule(new SharedPrefsModule())
                .appModule(new AppModule(this))
                .netModule(netModule)
                .build();

        mAppStorageComponent = DaggerAppStorageComponent.builder()
                .appStorageModule(new AppStorageModule())
                .appModule(new AppModule(this))
                .build();

        mDataComponent = DaggerDataComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .netModule(netModule)
                .build();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(netModule)
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public DataComponent getDataComponent() {
        return mDataComponent;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
