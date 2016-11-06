package org.d.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class ObservableUtil<T> {
    public Observable<T> asObservable(BehaviorSubject<T> subject) {
        return subject.asObservable().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }
}
