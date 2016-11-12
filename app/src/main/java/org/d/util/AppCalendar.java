package org.d.util;

import java.util.GregorianCalendar;

public class AppCalendar {
    public long now() {
        return new GregorianCalendar().getTimeInMillis();
    }
}
