package org.d.model;

import android.support.annotation.StringRes;

import org.d.R;

public enum MainTabs {
    KEYBOARD(R.string.keyboard), SAVED(R.string.saved);

    private final @StringRes
    int mTextId;

    MainTabs(@StringRes int text) {
        mTextId = text;
    }

    public int getTextId() {
        return mTextId;
    }

    public static final MainTabs[] ARRAY = MainTabs.values();
}
