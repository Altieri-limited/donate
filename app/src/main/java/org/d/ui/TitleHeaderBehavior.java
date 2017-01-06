package org.d.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import org.d.ui.widget.HeaderViewBehavior;
import org.d.ui.widget.HeaderWidget;

@SuppressWarnings("unused")
public class TitleHeaderBehavior extends HeaderViewBehavior<HeaderWidget> {

    private static final float MAX_PERCENTAGE_FOR_CLICK = 0.1f;
    private static final float MAX_PERCENTAGE_FOR_SCROLL = 0.02f;

    public TitleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, HeaderWidget child, View dependency) {
        int maxScroll = ((AppBarLayout) dependency).getTotalScrollRange();
        float percentage = Math.abs(dependency.getY()) / (float) maxScroll;
        child.clickListenersEnabled(percentage < MAX_PERCENTAGE_FOR_CLICK);
        child.onScrolled(percentage < MAX_PERCENTAGE_FOR_SCROLL);
        return super.onDependentViewChanged(parent, child, dependency);
    }

}
