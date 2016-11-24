package org.d.model.lycs;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.ArrayList;

@AutoValue
public abstract class Charities implements Parcelable {
    public abstract ArrayList<Charity> getCharities();

    public static TypeAdapter<Charities> typeAdapter(Gson gson) {
        return new AutoValue_Charities.GsonTypeAdapter(gson);
    }

    public int size() {
        return getCharities() != null ? getCharities().size() : 0;
    }

    public Charity get(int position) {
        return getCharities().get(position);
    }

    public static Charities create() {
        return new AutoValue_Charities(new ArrayList<>());
    }
}
