package org.d.data.storage;

import org.d.model.MoneySaved;

import java.util.ArrayList;
import java.util.List;

public class AppStorage {
    public static AppStorage getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void save(MoneySaved moneySaved) {
    }

    public List<MoneySaved> listSavings() {
        return new ArrayList<>();
    }

    private static class InstanceHolder {
        static final AppStorage INSTANCE = new AppStorage();
    }
}
