package com.granzotto.marcio.weatherapp.modules.main;

import com.granzotto.marcio.weatherapp.models.City;
import com.granzotto.marcio.weatherapp.modules.base.BaseContracts;

import java.util.HashMap;
import java.util.List;

public interface MainContracts {

	interface View extends BaseContracts.View {
		void showCities(List<City> cities, HashMap<String, Double> temperatures);
	}

	interface Presenter extends BaseContracts.Presenter {
		void onSwipeToRefreshTriggered();
	}

}
