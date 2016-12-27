package org.d.data;

import org.d.util.AppCalendar;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PiggyBank {
    private AppData mAppData;
    private AppCalendar mCalendar;
    private double mMoneyToSave = 0;

    @Inject
    public PiggyBank(AppData appData, AppCalendar calendar) {
        mAppData = appData;
        mCalendar = calendar;
    }

    public void store(Observer<Void> observer) {
        boolean saved = mMoneyToSave != 0;
        if (saved) {
            mAppData.save(mMoneyToSave, mCalendar.now(), observer);
            mMoneyToSave = 0;
        } else {
            observer.onCompleted();
        }
    }

    public void getTotalMoneySaved(Action1<Double> subscriber) {
        Observable<Double> observable = mAppData.getTotalPending();
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(subscriber);
    }
    public void add(double moneySaved) {
        mMoneyToSave += moneySaved;
    }

    public void set(double moneySaved) {
        mMoneyToSave = moneySaved;
    }

    public double getMoneyToSave() {
        return mMoneyToSave;
    }

}
