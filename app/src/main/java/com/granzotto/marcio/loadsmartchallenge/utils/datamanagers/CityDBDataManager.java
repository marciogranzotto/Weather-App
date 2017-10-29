package com.granzotto.marcio.loadsmartchallenge.utils.datamanagers;

import com.granzotto.marcio.loadsmartchallenge.models.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;

public class CityDBDataManager {

	private Realm realm = Realm.getDefaultInstance();

	public Flowable<List<City>> fetchCities() {
		return realm.where(City.class)
				.findAllAsync()
				.asFlowable()
				.observeOn(AndroidSchedulers.mainThread())
				.map(results -> realm.copyFromRealm(results));
	}

	public void saveCity(City city, SaveListener listener) {
		realm.executeTransactionAsync(
				realm -> realm.copyToRealm(city),
				listener::onSuccess,
				listener::onError
		);
	}

	public interface SaveListener {
		void onSuccess();

		void onError(Throwable throwable);
	}

}
