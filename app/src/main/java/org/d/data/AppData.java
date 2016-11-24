package org.d.data;

import android.support.annotation.NonNull;

import org.d.data.storage.AppStorage;
import org.d.model.lycs.Charities;
import org.d.model.lycs.Charity;
import org.d.network.TheLifeYouCanSaveService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppData {
    private final AppStorage mAppStorage;
    private TheLifeYouCanSaveService mServiceTLYCS;

    @NonNull private ArrayList<Charity> mCharities = new ArrayList<>();

    @Inject public AppData(AppStorage appStorage, TheLifeYouCanSaveService serviceTLYCS) {
        mAppStorage = appStorage;
        mServiceTLYCS = serviceTLYCS;
    }

    public void getCharities(Observer<? super List<Charity>> subscriber) {
        if (mCharities.size() == 0) {
            mAppStorage.getCharities(new Observer<ArrayList<Charity>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ArrayList<Charity> charities) {
                    if (charities.size() > 0) {
                        mCharities = charities;
                        subscriber.onNext(mCharities);
                    } else {
                        mServiceTLYCS.getCharities()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(Charities::getCharities)
                                .subscribe(charities1 -> {
                                    mAppStorage.save(charities1);
                                    subscriber.onNext(charities1);
                                });
                    }
                }
            });
        } else {
            subscriber.onNext(mCharities);
        }
    }

}
