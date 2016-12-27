package org.d.model;

import android.os.Message;
import android.os.Parcel;

public class MoneySavedAdapterEntry extends MoneySaved {
    private Message mMessage;

    protected MoneySavedAdapterEntry(Parcel in) {
        super(in);
        mMessage = in.readParcelable(Message.class.getClassLoader());
    }

    public MoneySavedAdapterEntry(double money, long time) {
        super(money, time);
        mMessage = null;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mMessage, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MoneySavedAdapterEntry> CREATOR = new Creator<MoneySavedAdapterEntry>() {
        @Override
        public MoneySavedAdapterEntry createFromParcel(Parcel in) {
            return new MoneySavedAdapterEntry(in);
        }

        @Override
        public MoneySavedAdapterEntry[] newArray(int size) {
            return new MoneySavedAdapterEntry[size];
        }
    };

    public Message getMessage() {
        return mMessage;
    }

    public boolean pendingRemove() {
        return mMessage != null;
    }

    public void setMessage(Message message) {
        mMessage = message;
    }
}
