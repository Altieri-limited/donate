package org.d.ui.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.d.model.MainTabs;
import org.d.model.MoneySavedOption;
import org.d.ui.fragment.KeyboardFragment;
import org.d.ui.fragment.SavedGridFragment;

import java.util.ArrayList;

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            case 2:
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
        }
    }

    @Override
    public int getCount() {
        return MainTabs.ARRAY.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(MainTabs.ARRAY[position].getTextId());
    }
}
