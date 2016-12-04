
package org.d.model.lycs;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Text implements Parcelable {
    @Nullable public abstract @SerializedName("single") String getSingle();
    @Nullable public abstract @SerializedName("plural") String getPlural();

    public static TypeAdapter<Text> typeAdapter(Gson gson) {
        return new AutoValue_Text.GsonTypeAdapter(gson);
    }

    public static Text create(String single, String plural) {
        return new AutoValue_Text(single, plural);
    }

    abstract public Text withPlural(String plural);
}
