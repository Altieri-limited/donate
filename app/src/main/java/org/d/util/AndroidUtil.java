package org.d.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import timber.log.Timber;

public class AndroidUtil {
    @Inject Context mContext;

    @Inject
    public AndroidUtil(Context context) {
        mContext = context;
    }

    public String loadStringFromAsset(String filename) {
        String json;
        try {
            InputStream is = mContext.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Timber.e(ex, "Exception reading asset file = %1$s", filename);
            return null;
        }
        return json;
    }
}
