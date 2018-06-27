package com.granzotto.marcio.weatherapp.modules.add_city;

import android.util.Log;

import com.granzotto.marcio.weatherapp.models.City;
import com.granzotto.marcio.weatherapp.models.WeatherUnit;
import com.granzotto.marcio.weatherapp.utils.datamanagers.CityDBDataManager;
import com.granzotto.marcio.weatherapp.utils.datamanagers.FlickrApiDataManager;
import com.granzotto.marcio.weatherapp.utils.datamanagers.WeatherApiDataManager;

import java.lang.ref.WeakReference;

public class AddCityPresenter implements AddCityContracts.Presenter {

	private WeakReference<AddCityContracts.View> view;
	private WeatherApiDataManager weatherApi;
	private CityDBDataManager dbDataManager;
	private FlickrApiDataManager flickrApiDataManager;
	private City city;

	AddCityPresenter(AddCityContracts.View view) {
		this(view, new WeatherApiDataManager(WeatherUnit.FAHRENHEIT), new CityDBDataManager(), new FlickrApiDataManager());
	}

	public AddCityPresenter(AddCityContracts.View view, WeatherApiDataManager weatherApi, CityDBDataManager dbDataManager, FlickrApiDataManager flickrApiDataManager) {
		this.view = new WeakReference<>(view);
		this.weatherApi = weatherApi;
		this.dbDataManager = dbDataManager;
		this.flickrApiDataManager = flickrApiDataManager;
	}

	@Override
	public void onCreate() {
		//does nothing
	}

	@Override
	public void onDestroy() {
		this.view.clear();
	}

	//region Presenter Contract

	@Override
	public void onSaveButtonClicked(String cityName, String state) {
		AddCityContracts.View weakView = view.get();
		if (weakView == null) return;
		weakView.showLoadingDialog();
		city = new City(cityName, state);
		weatherApi.fetchCityId(cityName)
				.subscribe(
						this::onCityIdFetched,
						error -> weakView.showErrorDialog(error.getMessage())
				);
	}

	//endregion

	//region Private

	private void onCityIdFetched(String id) {
		AddCityContracts.View weakView = view.get();
		if (weakView == null) return;
		city.setId(id);
		flickrApiDataManager.fetchImageUrl(city.getName() + ", " + city.getStateName())
				.subscribe(
						this::onCityImageFetched,
						error -> weakView.showErrorDialog(error.getMessage())
				);
	}

	private void onCityImageFetched(String imgUrl) {
		AddCityContracts.View weakView = view.get();
		if (weakView == null) return;
		city.setImageUrl(imgUrl);
		dbDataManager.saveCity(city, new CityDBDataManager.SaveListener() {
			@Override
			public void onSuccess() {
				weakView.hideLoadingDialog();
				weakView.closeScreen();
			}

			@Override
			public void onError(Throwable throwable) {
				Log.e("SAVE_CITY", "error", throwable);
				weakView.showErrorDialog(throwable.getMessage());
			}
		});
	}

	//endregion
}
