package org.d.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoneySaved implements Parcelable {
    private double Money;
    private String Time;

    protected MoneySaved(Parcel in) {
        Money = in.readDouble();
        Time = in.readString();
    }

    public MoneySaved(double money, String time) {
        Money = money;
        Time = time;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Money);
        dest.writeString(Time);
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
        Money = money;
    }

    public void setTime(String time) {
        Time = time;
    }

    public double getMoney() {
        return Money;
    }

    public String getTime() {
        return Time;
    }

}
