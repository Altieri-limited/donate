package org.d.model.lycs;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class Charities implements Parcelable {
    public abstract List<Charity> getCharities();

    public static TypeAdapter<Charities> typeAdapter(Gson gson) {
        return new AutoValue_Charities.GsonTypeAdapter(gson);
    }

}
