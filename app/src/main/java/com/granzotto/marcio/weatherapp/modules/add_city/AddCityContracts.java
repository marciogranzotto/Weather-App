package com.granzotto.marcio.weatherapp.modules.add_city;

import com.granzotto.marcio.weatherapp.modules.base.BaseContracts;

public interface AddCityContracts {

	interface View extends BaseContracts.View {
		void closeScreen();
	}

	interface Presenter extends BaseContracts.Presenter {
		void onSaveButtonClicked(String cityName, String state);
	}
}
