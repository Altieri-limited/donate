package org.d.data.storage;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public final class RealmObservable {
    private RealmObservable() {
    }

    /**
     *
     * @param function taking realm returning object of type <T>
     * @param <T> realm object type
     * @return Observable
     */
    public static <T extends RealmObject> Observable<T> object(final Func1<Realm, T> function) {
        return Observable.create(new OnSubscribeRealm<T>() {
            @Override
            public T get(Realm realm) {
                return function.call(realm);
            }
        });
    }

    /**
     *
     * @param action to be performed on the realm database
     * @return Observable
     */
    public static Observable<Void> call(Action1<Realm> action) {
        return Observable.create(new OnSubscribeRealm<Void>() {
            @Override
            public Void get(Realm realm) {
                action.call(realm);
                return null;
            }
        });
    }

    /**
     *
     * @param function taking realm returning object of type <T extends Number>
     * @param <T> object of type Number
     * @return Observable
     */
    public static <T extends Number> Observable<T> number(final Func1<Realm, T> function) {
        return Observable.create(new OnSubscribeRealm<T>() {
            @Override
            public T get(Realm realm) {
                return function.call(realm);
            }
        });
    }

    public static <T extends RealmObject> Observable<RealmList<T>> list(final Func1<Realm, RealmList<T>> function) {
        return Observable.create(new OnSubscribeRealm<RealmList<T>>() {
            @Override
            public RealmList<T> get(Realm realm) {
                return function.call(realm);
            }
        });
    }

    public static <T extends RealmObject> Observable<RealmList<T>> list(String fileName, final Func1<Realm, RealmList<T>> function) {
        return Observable.create(new OnSubscribeRealm<RealmList<T>>(fileName) {
            @Override
            public RealmList<T> get(Realm realm) {
                return function.call(realm);
            }
        });
    }

    public static <T extends RealmObject> Observable<RealmResults<T>> results(final Func1<Realm, RealmResults<T>> function) {
        return Observable.create(new OnSubscribeRealm<RealmResults<T>>() {
            @Override
            public RealmResults<T> get(Realm realm) {
                return function.call(realm);
            }
        });
    }

}