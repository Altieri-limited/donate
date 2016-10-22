package org.d.data;

public class PiggyBank {
    private double mMoneySaved = 0;

    public void store() {
        AppSharedPrefs.getInstance().saveMoney(mMoneySaved);
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
}
