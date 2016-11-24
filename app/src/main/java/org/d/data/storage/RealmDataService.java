package org.d.data.storage;

import org.d.model.lycs.Charity;

import java.util.ArrayList;

import rx.Observable;

public class RealmDataService implements DataService {

    public RealmDataService() {
    }

    @Override
    public Observable<ArrayList<Charity>> charities() {
        return RealmObservable.results(realm -> realm.where(RealmCharity.class).findAll()).map(realmCharities -> {
            final ArrayList<Charity> charities = new ArrayList<>(realmCharities.size());
            for (RealmCharity realmCharity : realmCharities) {
                charities.add(fromRealm(realmCharity));
            }
            return charities;
        });
    }

    private Charity fromRealm(RealmCharity realmCharity) {
        return Charity.create(realmCharity.getName(), realmCharity.getId(),
                realmCharity.getOverhead(), realmCharity.getInfoURL(),
                realmCharity.getDonateURL(), realmCharity.getLogo(),
                realmCharity.getDefaultText(), realmCharity.getRecommendation(),
                realmCharity.getNumbers(), realmCharity.getDefaultText(),
                realmCharity.getPricePoints());
    }

    @Override
    public void storeCharity(Charity charity) {
        RealmObservable.object(realm -> {
            RealmCharity realmCharity = realm.createObject(RealmCharity.class);
            realmCharity.set(charity.getName(), charity.getId(),
                    charity.getOverhead(), charity.getInfoURL(),
                    charity.getDonateURL(), charity.getLogo(),
                    charity.getDefaultText(), charity.getRecommendation(),
                    charity.getNumbers(), charity.getDefaultText(),
                    charity.getPricePoints());
            return realmCharity;
        });
    }

    public void storeCharities(ArrayList<Charity> charities) {
        for (Charity charity: charities) {
            storeCharity(charity);
        }

    }



}