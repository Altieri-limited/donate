package org.d.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MoneySavedOption implements Parcelable {
    public abstract String getText();
    public abstract double getValue();

    public static MoneySavedOption create(double value) {
        String text = value < 1 ? String.valueOf(value) : String.valueOf((int)value);
        return new AutoValue_MoneySavedOption(text, value);
    }

}
