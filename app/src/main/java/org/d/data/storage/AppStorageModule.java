package org.d.data.storage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class AppStorageModule {

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    AppStorage provideAppStorage(RealmDataService ds, Realm realm) {
        return new AppStorage(ds, realm);
    }

    @Provides
    @Singleton
    public RealmDataService provideRealmDataService() {
        return new RealmDataService();
    }
}
