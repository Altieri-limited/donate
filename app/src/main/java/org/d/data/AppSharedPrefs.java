package org.d.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.d.App;

public class AppSharedPrefs {
    private static final String SAVED_MONEY = "saved_money";
    private SharedPreferences mPrefs;

    private static class InstanceHolder {
        private static AppSharedPrefs APP_SHARED_REF_INSTANCE = new AppSharedPrefs(App.getInstance());
    }

    public static AppSharedPrefs getInstance(){
        return InstanceHolder.APP_SHARED_REF_INSTANCE;
    }

    private AppSharedPrefs(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

}
