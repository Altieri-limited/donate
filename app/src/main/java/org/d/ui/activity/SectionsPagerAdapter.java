package org.d.ui.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.d.App;
import org.d.data.AppData;
import org.d.model.MainTabs;
import org.d.model.MoneySavedOption;
import org.d.ui.fragment.CharitiesFragment;
import org.d.ui.fragment.KeyboardFragment;
import org.d.ui.fragment.SavedGridFragment;

import java.util.ArrayList;

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
                ArrayList<MoneySavedOption> options = new ArrayList<>();
                options.add(MoneySavedOption.create(0.1));
                options.add(MoneySavedOption.create(0.5));
                options.add(MoneySavedOption.create(1));
                options.add(MoneySavedOption.create(10));
                options.add(MoneySavedOption.create(20));
                options.add(MoneySavedOption.create(50));
                options.add(MoneySavedOption.create(100));
                options.add(MoneySavedOption.create(1000));
                return SavedGridFragment.newInstance(options);
            case 1:
                return KeyboardFragment.newInstance();
            case 2:
                return CharitiesFragment.newInstance();
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
