package org.d.data;

import org.d.data.storage.AppStorage;
import org.d.model.MoneySaved;
import org.d.util.AppCalendar;

import java.util.List;

import javax.inject.Inject;

public class PiggyBank {
    private AppStorage mAppStorage;
    private AppCalendar mCalendar;
    private double mMoneySaved = 0;

    @Inject
    public PiggyBank(AppStorage appStorage, AppCalendar calendar) {
        mAppStorage = appStorage;
        mCalendar = calendar;
    }

    public void store() {
        mAppStorage.save(mMoneySaved, mCalendar.now());
        mMoneySaved = 0;
    }

    public void add(double moneySaved) {
        mMoneySaved += moneySaved;
    }

    public void set(double moneySaved) {
        mMoneySaved = moneySaved;
    }

    public double getMoneySaved() {
        return mMoneySaved;
    }

    public double getTotalPending() {
        return mAppStorage.getTotalPending();
    }

    public List<MoneySaved> listAll() {
        return mAppStorage.listSavings();
    }
}
