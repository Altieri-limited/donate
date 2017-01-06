package org.d.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.d.App;
import org.d.R;
import org.d.util.UiUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleHeaderWidget extends HeaderWidget {

    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.subtitle) TextView mSubtitle;

    @Inject UiUtil mUiUtil;

    public TitleHeaderWidget(Context context) {
        super(context);
        inflateLayout(context);

    }

    public TitleHeaderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context);
    }

    public TitleHeaderWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleHeaderWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateLayout(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setTitle(String text) {
        setText(mTitle, text);
    }

    public void setSubtitle(String text) {
        setText(mSubtitle, text);
    }

    private void setText(TextView textView, String text) {
        mUiUtil.htmlFromHtml(getContext(), textView, text);
    }

    @Override
    public void setScaleXTitle(float scaleXTitle) {
        mTitle.setScaleX(scaleXTitle);
        mTitle.setPivotX(0);
    }

    @Override
    public void setScaleYTitle(float scaleYTitle) {
        mTitle.setScaleY(scaleYTitle);
        mTitle.setPivotY(30);
    }

    @Override
    public void clickListenersEnabled(boolean enabled) {

    }

    @Override
    public void onScrolled(boolean alreadyScrolled) {
    }

    private void inflateLayout(Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_header_layout, this, true);
        ButterKnife.bind(this);
    }

}

