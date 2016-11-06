package org.d.data;

import org.d.data.storage.AppStorage;
import org.d.model.MoneySaved;

import java.util.GregorianCalendar;
import java.util.List;

public class PiggyBank {
    private final AppStorage mAppStorage;
    private double mMoneySaved = 0;

    public PiggyBank() {
        mAppStorage = AppStorage.getInstance();
    }

    public void store() {
        GregorianCalendar calendar = new GregorianCalendar();
        MoneySaved moneySaved = MoneySaved.create(mMoneySaved, calendar.getTimeInMillis());
        mAppStorage.save(moneySaved);
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

    public List<MoneySaved> listAll() {
        return mAppStorage.listSavings();
    }
}
