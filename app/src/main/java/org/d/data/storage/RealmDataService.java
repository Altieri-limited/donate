package org.d.data.storage;

import android.support.annotation.NonNull;

import org.d.model.MoneySaved;
import org.d.model.lycs.Charity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RealmDataService implements DataService {

    public RealmDataService() {
    }

    @Override
    public Observable<ArrayList<Charity>> charities() {
        return RealmObservable.results(realm -> realm.where(RealmCharity.class).findAll()).map(realmCharities -> {
            final ArrayList<Charity> charities = new ArrayList<>(realmCharities.size());
            for (RealmCharity realmCharity : realmCharities) {
                charities.add(fromRealm(realmCharity));
            }
            return charities;
        });
    }

    @Override
    public void storeCharity(Charity charity) {
        Observable<RealmCharity> observable = RealmObservable.object(realm -> {
            RealmCharity realmCharity = fromCharity(realm, charity);
            return realmCharity;
        });
        observable.subscribeOn(Schedulers.io()).subscribe(realmCharity -> Timber.d(realmCharity.getName()));
    }

    @Override
    public void storeMoneySaved(double money, String time, Observer<Void> observer) {
        Observable<RealmMoneySaved> observable = RealmObservable.object(realm -> {
            RealmMoneySaved moneySaved = realm.createObject(RealmMoneySaved.class);
            moneySaved.setMoney(money);
            moneySaved.setTime(time);
            return moneySaved;
        });
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<RealmMoneySaved>() {
                    @Override
                    public void onCompleted() {
                        observable.doOnCompleted(null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onNext(RealmMoneySaved realmMoneySaved) {
                        observer.onNext(null);
                    }
                });

    }

    public void storeCharities(ArrayList<Charity> charities) {
        for (Charity charity: charities) {
            storeCharity(charity);
        }

    }


    @NonNull
    private RealmCharity fromCharity(Realm realm, Charity charity) {
        RealmCharity realmCharity = realm.createObject(RealmCharity.class, charity.getId());
        realmCharity.set(charity.getName(), charity.getOverhead(), charity.getInfoURL(),
                charity.getDonateURL(), charity.getLogo(),
                charity.getOrganization(), charity.getRecommendation(),
                charity.getNumbers(), charity.getDefaultText(),
                charity.getPricePoints());
        return realmCharity;
    }

    private Charity fromRealm(RealmCharity realmCharity) {
        return Charity.create(realmCharity.getName(), realmCharity.getId(),
                realmCharity.getOverhead(), realmCharity.getInfoURL(),
                realmCharity.getDonateURL(), realmCharity.getLogo(),
                realmCharity.getOrganization(), realmCharity.getRecommendation(),
                realmCharity.getEvidence(),
                realmCharity.getNumbers(), realmCharity.getDefaultText(),
                realmCharity.getPricePoints());
    }

    @Override
    public Observable<Double> getTotalPending() {
        return RealmObservable.number(realm -> {
            double total = realm.where(RealmMoneySaved.class).sum(RealmMoneySaved.FIELDS.MONEY).doubleValue();
            return total;
        });
    }

    @Override
    public void remove(String timeRemoved) {
        RealmObservable.results(realm -> {
            RealmResults<RealmMoneySaved> moneySaved = realm.where(RealmMoneySaved.class).equalTo(RealmMoneySaved.FIELDS.TIME, timeRemoved).findAll();
            moneySaved.deleteAllFromRealm();
            return moneySaved;
        }).subscribe();
    }

    public Observable<ArrayList<MoneySaved>> savings() {
        return RealmObservable.results(realm -> realm.where(RealmMoneySaved.class).findAll()).map(realmSavings -> {
            final ArrayList<MoneySaved> savings = new ArrayList<>(realmSavings.size());
            for (RealmMoneySaved realmMoneySaved : realmSavings) {
                savings.add(fromRealm(realmMoneySaved));
            }
            return savings;
        });
    }

    private MoneySaved fromRealm(RealmMoneySaved moneySaved) {
        return new MoneySaved(moneySaved.getMoney(), moneySaved.getTime());
    }

}