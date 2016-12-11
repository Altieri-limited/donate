package org.d.data.storage;

import org.d.model.lycs.PricePoint;
import org.d.model.lycs.RealmPricePoint;
import org.d.model.lycs.Text;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmCharity extends RealmObject {
    private String mName;
    @PrimaryKey private String mId;
    private Double mOverhead;
    private String mInfoURL;
    private String mDonateURL;
    private String mLogo;
    private String mOrganization;
    private String mDefaultText;
    private String mRecommendation;
    private String mEvidence;
    private String mNumbers;
    private RealmList<RealmPricePoint> mPricePoints;

    public RealmCharity() {

    }

    public void  set(String name, Double overhead, String infoURL, String donateURL, String logo, String organization, String recommendation, String numbers, String defaultText, List<PricePoint> pricePoints) {
        mName = name;
        mOverhead = overhead;
        mInfoURL = infoURL;
        mDonateURL = donateURL;
        mLogo = logo;
        mOrganization = organization;
        mRecommendation = recommendation;
        mNumbers = numbers;
        mDefaultText = defaultText;
        mPricePoints = new RealmList<>();
        for (PricePoint pp: pricePoints) {
            mPricePoints.add(new RealmPricePoint(pp.getPrice(), pp.getIconURL(), pp.getColor(), pp.getText(), pp.getJoiner()));
        }
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public Double getOverhead() {
        return mOverhead;
    }

    public String getInfoURL() {
        return mInfoURL;
    }

    public String getDonateURL() {
        return mDonateURL;
    }

    public String getLogo() {
        return mLogo;
    }

    public String getDefaultText() {
        return mDefaultText;
    }

    public String getRecommendation() {
        return mRecommendation;
    }

    public String getEvidence() { return mEvidence; }

    public String getNumbers() {
        return mNumbers;
    }

    public List<PricePoint> getPricePoints() {
        ArrayList<PricePoint> pricePoints = new ArrayList<>();
        for (RealmPricePoint pp: mPricePoints) {
            Text text = Text.create(pp.text.getSingle(), pp.text.getPlural());
            pricePoints.add(PricePoint.create(pp.price, pp.iconURL, pp.color, text, pp.joiner));
        }
        return pricePoints;
    }

}
