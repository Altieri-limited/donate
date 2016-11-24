
package org.d.model.lycs;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
abstract public class PricePoint implements Parcelable {
    @Nullable public abstract @SerializedName("price") Double getPrice();
    @Nullable public abstract @SerializedName("iconURL") String getIconURL();
    @Nullable public abstract @SerializedName("color") String getColor();
    @Nullable public abstract @SerializedName("text") Text getText();
    @Nullable public abstract @SerializedName("joiner") String getJoiner();

    public static TypeAdapter<PricePoint> typeAdapter(Gson gson) {
        return new AutoValue_PricePoint.GsonTypeAdapter(gson);
    }

    public static PricePoint create(Double price, String iconURL, String color, Text text, String joiner) {
        return new AutoValue_PricePoint(price, iconURL, color, text, joiner);
    }
}
