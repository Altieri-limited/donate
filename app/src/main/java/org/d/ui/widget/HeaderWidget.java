package org.d.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class HeaderWidget extends LinearLayout {
    public HeaderWidget(Context context) {
        super(context);
    }

    public HeaderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public abstract void setScaleXTitle(float scaleXTitle);

    public abstract void setScaleYTitle(float scaleYTitle);

    public abstract void clickListenersEnabled(boolean enabled);

    public abstract void onScrolled(boolean scrolled);
}
