package com.granzotto.marcio.loadsmartchallenge.modules.add_city;

import java.lang.ref.WeakReference;

public class AddCityPresenter implements AddCityContracts.Presenter {

	private WeakReference<AddCityContracts.View> view;

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
}
