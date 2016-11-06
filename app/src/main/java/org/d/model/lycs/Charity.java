
package org.d.model.lycs;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Charity implements Parcelable {
    @Nullable public abstract @SerializedName("name") String getName();
    @Nullable public abstract @SerializedName("id") String getId();
    @Nullable public abstract @SerializedName("overhead") Double getOverhead();
    @Nullable public abstract @SerializedName("infoURL") String getInfoURL();
    @Nullable public abstract @SerializedName("donateURL") String getDonateURL();
    @Nullable public abstract @SerializedName("logo") String getLogo();
    @Nullable public abstract @SerializedName("organization") String getorganization();
    @Nullable public abstract @SerializedName("recommendation") String getRecommendation();
    @Nullable public abstract @SerializedName("numbers") String getNumbers();
    @Nullable public abstract @SerializedName("defaultText") String getDefaultText();
    @Nullable public abstract @SerializedName("pricePoints") List<PricePoint> getPricePoints();

    public static TypeAdapter<Charity> typeAdapter(Gson gson) {
        return new AutoValue_Charity.GsonTypeAdapter(gson);
    }
}
