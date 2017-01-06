package org.d.network.tlycs;

public class TheLifeYouCanSaveHelper {
    private String mBaseUrl;

    public TheLifeYouCanSaveHelper(String baseUrl) {

        mBaseUrl = baseUrl;
    }

    public String getImageUrl(String iconURL) {
        return mBaseUrl + iconURL;
    }
}
