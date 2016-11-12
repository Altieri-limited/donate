package org.d.data.storage;

import org.d.model.MoneySaved;
import org.d.model.lycs.Charity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppStorage {
    private ArrayList<Charity> mCharities;
    @Inject RealmDataService mRealmDataService;
    @Inject Realm mRealm;

    AppStorage(RealmDataService realmDataService, Realm realm) {
        mRealmDataService = realmDataService;
        mRealm = realm;
    }

    public double getTotalPending() {
        double total = mRealm.where(MoneySaved.class).sum(MoneySaved.FIELDS.MONEY).doubleValue();
        return total;
    }

    public List<MoneySaved> listSavings() {
        RealmResults<MoneySaved> result = mRealm.where(MoneySaved.class).findAll();
        int size = result.size();
        return Arrays.asList(result.toArray(new MoneySaved[size]));
    }

    public void save(double saved, long timeInMillis) {
        mRealm.beginTransaction();
        MoneySaved moneySaved = mRealm.createObject(MoneySaved.class);
        moneySaved.setMoney(saved);
        moneySaved.setTime(timeInMillis);
        mRealm.commitTransaction();
    }

    public void getCharities(Observer<? super ArrayList<Charity>> subscriber) {
        if (mCharities == null) {

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
