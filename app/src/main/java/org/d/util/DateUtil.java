package org.d.util;

import android.content.Context;
import android.text.format.DateUtils;

import javax.inject.Inject;

import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;

public class DateUtil {

    @Inject Context mContext;

    @Inject
    public DateUtil(Context context) {
        mContext = context;
    }

    public String formatDateTime(long time) {
        return DateUtils.formatDateTime(mContext, time, FORMAT_NUMERIC_DATE);
    }
}
