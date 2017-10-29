package com.granzotto.marcio.loadsmartchallenge.modules.add_city;

import com.granzotto.marcio.loadsmartchallenge.models.City;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.WeatherApiDataManager;

import java.lang.ref.WeakReference;

public class AddCityPresenter implements AddCityContracts.Presenter {

	private WeakReference<AddCityContracts.View> view;
	private WeatherApiDataManager weatherApi = new WeatherApiDataManager(WeatherUnit.FAHRENHEIT);
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
		//TODO get the image
		//TODO save city to database
		weakView.hideLoadingDialog();
		weakView.closeScreen();
	}

	//endregion
}
