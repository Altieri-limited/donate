package org.d.data.storage;

import org.d.model.lycs.Charity;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;

public interface DataService {
    Observable<ArrayList<Charity>> charities();

    void storeCharity(Charity charity);

    void storeMoneySaved(double money, String time, Observer<Void> observer);

    Observable<Double> getTotalPending();

    void remove(String timeRemoved);
}