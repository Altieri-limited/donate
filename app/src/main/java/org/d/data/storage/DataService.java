package org.d.data.storage;

import org.d.model.lycs.Charity;

import java.util.ArrayList;

import rx.Observable;

public interface DataService {
    Observable<ArrayList<Charity>> charities();
    void storeCharity(Charity charity);
}