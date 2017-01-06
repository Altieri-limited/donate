package org.d.data;

import android.support.annotation.NonNull;

import org.d.data.storage.AppStorage;
import org.d.model.MoneySaved;
import org.d.model.lycs.Charity;
import org.d.model.lycs.PricePoint;
import org.d.model.lycs.Text;
import org.d.network.tlycs.TheLifeYouCanSaveService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AppData {
    private final AppStorage mAppStorage;
    private TheLifeYouCanSaveService mServiceTLYCS;

    @NonNull private ArrayList<Charity> mCharities = new ArrayList<>();

    @Inject public AppData(AppStorage appStorage, TheLifeYouCanSaveService serviceTLYCS) {
        mAppStorage = appStorage;
        mServiceTLYCS = serviceTLYCS;
    }

    public void getCharities(Observer<? super ArrayList<Charity>> subscriber) {
        if (mCharities.size() == 0) {
            mAppStorage.getCharities(new Action1<ArrayList<Charity>>() {
                @Override
                public void call(ArrayList<Charity> charities) {
                    if (charities.size() > 0) {
                        mCharities = charities;
                        subscriber.onNext(mCharities);
                        subscriber.onCompleted();
                    } else {
                        mServiceTLYCS.getCharities()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(charitiesObject -> (charitiesObject.getCharities() != null) ? charitiesObject.getCharities() : new ArrayList<Charity>())
                                .subscribe(charitiesArray -> {
                                    ArrayList<Charity> androidCharities = new ArrayList<>(charities.size());
                                    for (Charity charity: charitiesArray) {
                                        if (charity.getPricePoints()!= null) {
                                            List<PricePoint> pricePoints = new ArrayList<>(charity.getPricePoints().size());
                                            for (PricePoint pp : charity.getPricePoints()) {
                                                Text text = pp.getText();
                                                PricePoint pricePoint;
                                                if (text != null && text.getPlural() != null) {
                                                    pricePoint = pp.withText(text.withPlural(text.getPlural().replace(" $1 ", " %s ").replace(" * ", " %s ")));
                                                } else {
                                                    pricePoint = pp.withText(Text.create("", ""));
                                                }
                                                pricePoints.add(pricePoint);
                                            }
                                            androidCharities.add(charity.withPricePoints(pricePoints));
                                        }
                                    }

                                    mAppStorage.save(androidCharities);
                                    subscriber.onNext(androidCharities);
                                    subscriber.onCompleted();
                                });
                    }
                }
            });
        } else {
            subscriber.onNext(mCharities);
            subscriber.onCompleted();
        }
    }


    public void save(double moneySaved, String now, Observer<Void> observer) {
        mAppStorage.save(moneySaved, now, observer);
    }

    public Observable<Double> getTotalPending() {
        return mAppStorage.getTotalPending();
    }

    public void listSavings(Action1<ArrayList<MoneySaved>> subscriber) {
        mAppStorage.listSavings(subscriber);
    }

    public void remove(String timeRemoved) {
        mAppStorage.removeSaving(timeRemoved);
    }
}
