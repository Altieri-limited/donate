
package org.d.model.lycs;

import io.realm.RealmObject;

public class RealmPricePoint extends RealmObject{
    public Double price;
    public String iconURL;
    public String color;
    public RealmText text;
    public String joiner;

    public RealmPricePoint() {
    }

    public RealmPricePoint(Double price, String iconURL, String color, Text text, String joiner) {
        this.price = price;
        this.iconURL = iconURL;
        this.color = color;
        this.text = new RealmText(text.getSingle(), text.getPlural());
        this.joiner = joiner;
    }
}
