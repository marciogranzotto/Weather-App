package com.granzotto.marcio.loadsmartchallenge.modules.main;

import android.content.Context;

import com.granzotto.marcio.loadsmartchallenge.models.City;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.CityDBDataManager;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.WeatherApiDataManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements MainContracts.Presenter {

	private WeakReference<MainContracts.View> view;

	private CityDBDataManager dbDataManager;
	private WeatherApiDataManager weatherDataManager;
	private List<City> cities = new ArrayList<>();
	private HashMap<String, Double> weatherMap = new HashMap<>();

	private CompositeDisposable compositeDisposable = new CompositeDisposable();

	public MainPresenter(MainContracts.View view) {
		this(view, new CityDBDataManager(), new WeatherApiDataManager(WeatherUnit.FAHRENHEIT));
	}

	public MainPresenter(MainContracts.View view, CityDBDataManager dbDataManager, WeatherApiDataManager weatherDataManager) {
		this.view = new WeakReference<>(view);
		this.dbDataManager = dbDataManager;
		this.weatherDataManager = weatherDataManager;
	}

	//region Presenter Contract

	@Override
	public void onCreate() {
		MainContracts.View weakView = view.get();
		if (weakView == null) return;
		createCitiesIfNone(dbDataManager, weakView.getActivityContext());
	}

	private void createCitiesIfNone(CityDBDataManager dbDataManager, Context activityContext) {
		dbDataManager.createCitiesIfNone(activityContext);
	}

	@Override
	public void onResume() {
		fetchCities();
	}

	@Override
	public void onDestroy() {
		if (!compositeDisposable.isDisposed()) compositeDisposable.clear();
		this.view.clear();
	}

	@Override
	public void onSwipeToRefreshTriggered() {
		fetchTemperatures();
	}

	//endregion

	//region Getters and Setters

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void setWeatherMap(HashMap<String, Double> weatherMap) {
		this.weatherMap = weatherMap;
	}

	public List<City> getCities() {
		return cities;
	}

	public HashMap<String, Double> getWeatherMap() {
		return weatherMap;
	}

	//endregion

	//region Private

	private void fetchCities() {
		MainContracts.View weakView = view.get();
		if (weakView != null) weakView.showLoadingDialog();

		if (!compositeDisposable.isDisposed()) compositeDisposable.clear();
		Disposable disposable = dbDataManager.fetchCities()
				.subscribe(
						this::onCitiesFetched,
						error -> {
							if (weakView != null) weakView.showErrorDialog(error.getMessage());
						}
				);
		compositeDisposable.add(disposable);
	}

	private void fetchTemperatures() {
		MainContracts.View weakView = view.get();

		//unfortunately we don't have a .map method in Java as we do in Kotlin, so...
		ArrayList<String> ids = new ArrayList<>();
		for (City city : getCities()) {
			ids.add(city.getId());
		}

		if (!compositeDisposable.isDisposed()) compositeDisposable.clear();
		Disposable disposable = weatherDataManager.fetchCurrentWeather(ids)
				.subscribe(
						this::onWeatherUpdated,
						error -> {
							if (weakView != null) weakView.showErrorDialog(error.getMessage());
						}
				);
		compositeDisposable.add(disposable);
	}

	private void onWeatherUpdated(HashMap<String, Double> weatherMap) {
		setWeatherMap(weatherMap);

		MainContracts.View weakView = view.get();
		if (weakView == null) return;

		weakView.hideLoadingDialog();
		weakView.showCities(getCities(), getWeatherMap());
	}

	private void onCitiesFetched(List<City> cities) {
		setCities(cities);

		MainContracts.View weakView = view.get();
		if (weakView == null) return;
		weakView.showCities(getCities(), getWeatherMap());

		fetchTemperatures();
	}

	//endregion
}
