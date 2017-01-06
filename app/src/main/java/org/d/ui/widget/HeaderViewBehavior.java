package org.d.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import org.d.R;

public abstract class HeaderViewBehavior <V extends HeaderWidget> extends CoordinatorLayout.Behavior<V> {
    private static final float MAX_SCALE = 0.5f;
    private final int mToolbarHeight;
    private int mStartMarginLeft;
    private int mEndMarginLeft;
    private int mMarginRight;
    private int mStartMarginBottom;
    private boolean mIsHidden;

    public HeaderViewBehavior(Context context) {
        int margin = R.dimen.default_margin;
        mStartMarginBottom = context.getResources().getDimensionPixelOffset(margin);
        mStartMarginLeft = context.getResources().getDimensionPixelOffset(margin);
        mMarginRight = context.getResources().getDimensionPixelOffset(margin);
        mEndMarginLeft = context.getResources().getDimensionPixelOffset(R.dimen.default_margin_double);
        mToolbarHeight = context.getResources().getDimensionPixelOffset(R.dimen.action_bar_height);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        int maxScroll = ((AppBarLayout) dependency).getTotalScrollRange();
        float percentage = Math.abs(dependency.getY()) / (float) maxScroll;
        // Set scale for the title
        float size = 1 - percentage * MAX_SCALE;
        child.setScaleXTitle(size);
        child.setScaleYTitle(size);

        // Set position for the header view
        float childPosition = dependency.getHeight() + dependency.getY() - child.getHeight() + (mToolbarHeight - child.getHeight()) * percentage / 2;

        childPosition = childPosition - mStartMarginBottom * (1f - percentage);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.leftMargin = (int) (percentage * mEndMarginLeft) + mStartMarginLeft;
        lp.rightMargin = mMarginRight;
        child.setLayoutParams(lp);
        child.setAlpha(1 - percentage);
        child.setY(childPosition);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (mIsHidden && percentage < 1) {
                child.setVisibility(View.VISIBLE);
                mIsHidden = false;
            } else if (!mIsHidden && percentage == 1) {
                child.setVisibility(View.GONE);
                mIsHidden = true;
            }
        }
        return true;
    }

}
