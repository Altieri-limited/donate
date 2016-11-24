package org.d.model.lycs;

import io.realm.RealmObject;

public class RealmText extends RealmObject{
    private String mSingle;
    private String mPlural;

    public RealmText() {

    }

    public RealmText(String single, String plural) {
        mSingle = single;
        mPlural = plural;
    }

    public String getSingle() {
        return mSingle;
    }

    public String getPlural() {
        return mPlural;
    }
}
