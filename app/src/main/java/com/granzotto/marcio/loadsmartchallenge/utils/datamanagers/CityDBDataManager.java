package com.granzotto.marcio.loadsmartchallenge.utils.datamanagers;

import android.content.Context;
import android.util.Log;

import com.granzotto.marcio.loadsmartchallenge.models.City;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;

public class CityDBDataManager {

	private Realm realm = Realm.getDefaultInstance();

	public Observable<List<City>> fetchCities() {
		return realm.where(City.class)
				.findAllAsync()
				.asFlowable()
				.toObservable()
				.observeOn(AndroidSchedulers.mainThread())
				.map(results -> realm.copyFromRealm(results));
	}

	public void saveCity(City city, SaveListener listener) {
		realm.executeTransactionAsync(
				realm -> realm.copyToRealmOrUpdate(city),
				listener::onSuccess,
				listener::onError
		);
	}

	public void createCitiesIfNone(Context context) {
		if (realm.where(City.class).count() > 0) return;
		InputStream input = null;
		try {
			input = context.getAssets().open("cities.json");
			realm.beginTransaction();
			realm.createOrUpdateAllFromJson(City.class, input);
			realm.commitTransaction();
		} catch (IOException e) {
			Log.e("createCitiesIfNone", "error creating cities!", e);
			realm.cancelTransaction();
		} finally {
			if (input != null) try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public interface SaveListener {
		void onSuccess();

		void onError(Throwable throwable);
	}

}
