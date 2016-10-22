package org.d.model;

import android.support.annotation.StringRes;

import org.d.R;

public enum MainTabs {
    GRID(R.string.i_saved), KEYBOARD(R.string.keyboard), TOTAL(R.string.totals);

    private final @StringRes
    int mTextId;

    MainTabs(@StringRes int text) {
        mTextId = text;
    }

    public int getTextId() {
        return mTextId;
    }

    public static final MainTabs[] ARRAY = {GRID, KEYBOARD, TOTAL};
}
