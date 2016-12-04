package org.d.data.storage;

import android.support.annotation.NonNull;

import org.d.model.MoneySaved;
import org.d.model.lycs.Charity;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppStorage {
    @NonNull private ArrayList<Charity> mCharities = new ArrayList<>();
    @Inject RealmDataService mRealmDataService;

    AppStorage(RealmDataService realmDataService, Realm realm) {
        mRealmDataService = realmDataService;
    }

    public Observable<Double> getTotalPending() {
        return mRealmDataService.getTotalPending();
    }

    public Observable<ArrayList<MoneySaved>> listSavings(Observer<? super ArrayList<MoneySaved>> subscriber) {
        Observable<ArrayList<MoneySaved>> observable = mRealmDataService.savings();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<MoneySaved>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<MoneySaved> savings) {
                        subscriber.onNext(savings);
                    }
                });
        return observable;
    }

    public void save(double saved, long timeInMillis) {
        mRealmDataService.storeMoneySaved(saved, timeInMillis);
    }

    public void getCharities(Observer<? super ArrayList<Charity>> subscriber) {
        if (mCharities.size() == 0) {

            Observable<ArrayList<Charity>> observable = mRealmDataService.charities();
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<Charity>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ArrayList<Charity> charities) {
                            mCharities = charities;
                            subscriber.onNext(mCharities);
                        }
                    });
        } else {
            subscriber.onNext(mCharities);
        }
    }

    public void save(ArrayList<Charity> charities) {
        mRealmDataService.storeCharities(charities);
    }
}
