package org.d.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MoneySaved {
    abstract double getMoneySaved();
    abstract long getTimeInMillis();

    public static MoneySaved create(double moneySaved, long timeInMillis) {
        return new AutoValue_MoneySaved( moneySaved, timeInMillis);
    }
}
