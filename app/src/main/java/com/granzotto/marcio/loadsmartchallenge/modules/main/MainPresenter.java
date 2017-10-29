package com.granzotto.marcio.loadsmartchallenge.modules.main;

import com.granzotto.marcio.loadsmartchallenge.models.City;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.CityDBDataManager;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.WeatherApiDataManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPresenter implements MainContracts.Presenter {

	private final WeakReference<MainContracts.View> view;
	private CityDBDataManager dbDataManager = new CityDBDataManager();
	private WeatherApiDataManager weatherDataManager = new WeatherApiDataManager(WeatherUnit.FAHRENHEIT);
	private List<City> cities;
	private HashMap<String, Double> weatherMap;

	MainPresenter(MainContracts.View view) {
		this.view = new WeakReference<>(view);
	}

	//region Presenter Contract

	@Override
	public void onCreate() {
		MainContracts.View weakView = view.get();
		if (weakView == null) return;
		dbDataManager.createCitiesIfNone(weakView.getActivityContext());
	}

	@Override
	public void onResume() {
		fetchCities();
	}

	@Override
	public void onDestroy() {
		this.view.clear();
	}

	@Override
	public void onSwipeToRefreshTriggered() {
		fetchTemperatures();
	}

	//endregion

	private void fetchCities() {
		MainContracts.View weakView = view.get();
		if (weakView == null) return;
		weakView.showLoadingDialog();
		dbDataManager.fetchCities()
				.subscribe(
						this::onCitiesFetched,
						error -> weakView.showErrorDialog(error.getMessage())
				);
	}

	private void fetchTemperatures() {
		MainContracts.View weakView = view.get();
		if (weakView == null) return;

		//unfortunately we don't have a .map method in Java as we do in Kotlin, so...
		ArrayList<String> ids = new ArrayList<>();
		for (City city : cities) {
			ids.add(city.getId());
		}

		weatherDataManager.fetchCurrentWeather(ids)
				.subscribe(
						this::onWeatherUpdated,
						error -> weakView.showErrorDialog(error.getMessage())
				);
	}

	private void onWeatherUpdated(HashMap<String, Double> weatherMap) {
		MainContracts.View weakView = view.get();
		if (weakView == null) return;

		this.weatherMap = weatherMap;
		weakView.hideLoadingDialog();
		weakView.showCities(cities, this.weatherMap);
	}

	private void onCitiesFetched(List<City> cities) {
		this.cities = cities;
		fetchTemperatures();
	}

}
