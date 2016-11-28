package org.d.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoneySaved implements Parcelable {
    private double Money;
    private long Time;

    public MoneySaved() {

    }

    protected MoneySaved(Parcel in) {
        Money = in.readDouble();
        Time = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Money);
        dest.writeLong(Time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MoneySaved> CREATOR = new Creator<MoneySaved>() {
        @Override
        public MoneySaved createFromParcel(Parcel in) {
            return new MoneySaved(in);
        }

        @Override
        public MoneySaved[] newArray(int size) {
            return new MoneySaved[size];
        }
    };

    public void setMoney(double money) {
        this.Money = money;
    }

    public void setTime(long time) {
        this.Time = time;
    }

    public double getMoney() {
        return Money;
    }

    public long getTime() {
        return Time;
    }

}
