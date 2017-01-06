package org.d.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import org.d.data.AppData;
import org.d.model.lycs.Charity;
import org.d.util.CompatUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observer;
import timber.log.Timber;

public class CharitiesHeaderWidget extends TitleHeaderWidget {
    @Inject
    CompatUtil mCompatUtil;
    @Inject
    AppData mAppData;

    ViewPager mCharitiesPager;
    private ArrayList<Charity> mCharities;
    private CharityActivityHeaderListener mListener;

    public CharitiesHeaderWidget(Context context) {
        super(context);
        init(context);
    }

    public CharitiesHeaderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CharitiesHeaderWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void setScaleXTitle(float scaleXTitle) {
        Timber.d("setScaleXTitle");
    }

    @Override
    public void setScaleYTitle(float scaleYTitle) {
        Timber.d("setScaleYTitle");
    }

    @Override
    public void clickListenersEnabled(boolean enabled) {
        Timber.d("clickListenersEnabled");
    }

    @Override
    public void onScrolled(boolean scrolled) {
        Timber.d("onScrolled");
    }

    private void init(Context context) {

        mAppData.getCharities(new Observer<ArrayList<Charity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<Charity> charities) {
                onNewCharity(context, charities);
            }
        });

    }

    private void onNewCharity(Context context, ArrayList<Charity> charities) {
        mCharities = charities;
        CharitiesAdapter charitiesAdapter = new CharitiesAdapter(context, mCharities);
        mCharitiesPager.setAdapter(charitiesAdapter);

        mCharitiesPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mListener.onCharitySelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
