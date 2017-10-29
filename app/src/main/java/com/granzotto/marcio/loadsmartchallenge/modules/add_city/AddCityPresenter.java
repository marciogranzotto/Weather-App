package com.granzotto.marcio.loadsmartchallenge.modules.add_city;

import android.util.Log;

import com.granzotto.marcio.loadsmartchallenge.models.City;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.CityDBDataManager;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.FlickrApiDataManager;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.WeatherApiDataManager;

import java.lang.ref.WeakReference;

public class AddCityPresenter implements AddCityContracts.Presenter {

	private WeakReference<AddCityContracts.View> view;
	private WeatherApiDataManager weatherApi = new WeatherApiDataManager(WeatherUnit.FAHRENHEIT);
	private CityDBDataManager dbDataManager = new CityDBDataManager();
	private City city;

	public AddCityPresenter(AddCityContracts.View view) {
		this.view = new WeakReference<>(view);
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
		FlickrApiDataManager.getInstance().fetchImageUrl(city.getName())
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
