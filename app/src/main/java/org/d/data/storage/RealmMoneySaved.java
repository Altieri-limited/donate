package org.d.data.storage;

import io.realm.RealmObject;

public class RealmMoneySaved extends RealmObject {
    final static class FIELDS {
        final static String MONEY = "Money";
        final static String TIME = "Time";
    }

    private double Money;
    private String Time;

    public RealmMoneySaved() {

    }

    public void setMoney(double money) {
        this.Money = money;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public double getMoney() {
        return Money;
    }

    public String getTime() {
        return Time;
    }

}
