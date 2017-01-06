package org.d.util;

import android.content.Context;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

public class DateUtil {
    public static final String DATE_TIME_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final SimpleDateFormat FULL_FORMAT = new SimpleDateFormat(DATE_TIME_WITH_TIME_ZONE, Locale.getDefault());
    private static final String DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm";

    @Inject Context mContext;

    @Inject
    public DateUtil(Context context) {
        mContext = context;
    }

    public String getCurrentTimeForDB(long time) {
        return FULL_FORMAT.format(time);
    }

    private LocalDateTime getLocalDateTime(String time) {
        try {
            return DateTimeFormat.forPattern(DATE_TIME_WITH_TIME_ZONE).parseLocalDateTime(time);
        } catch (RuntimeException e) {
            return new LocalDateTime(1,1,1,0,0);
        }
    }

    public String formatDateTime(String time) {
        return getLocalDateTime(time).toString(DATE_TIME_FORMAT);
    }

}
