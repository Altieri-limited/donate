package org.d.ui.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.d.App;
import org.d.data.AppData;
import org.d.model.MainTabs;
import org.d.ui.fragment.KeyboardFragment;
import org.d.ui.fragment.MoneyFragment;

import javax.inject.Inject;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private App mApp;
    @Inject AppData mAppData;

    SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mApp = (App) context.getApplicationContext();
        mApp.getDataComponent().inject(this);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return KeyboardFragment.newInstance();
            case 1:
                return MoneyFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return MainTabs.ARRAY.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mApp.getString(MainTabs.ARRAY[position].getTextId());
    }
}
