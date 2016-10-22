package org.d.util;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

public class CompatUtil {
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }
}