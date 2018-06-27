package com.granzotto.marcio.weatherapp.modules;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupRealm();
    }

    private void setupRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
