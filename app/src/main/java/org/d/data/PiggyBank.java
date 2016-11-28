package org.d.data;

import org.d.util.AppCalendar;

import javax.inject.Inject;

public class PiggyBank {
    private AppData mAppData;
    private AppCalendar mCalendar;
    private double mMoneyToSave = 0;

    @Inject
    public PiggyBank(AppData appData, AppCalendar calendar) {
        mAppData = appData;
        mCalendar = calendar;
    }

    public void store() {
        mAppData.save(mMoneyToSave, mCalendar.now());
        mMoneyToSave = 0;
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
