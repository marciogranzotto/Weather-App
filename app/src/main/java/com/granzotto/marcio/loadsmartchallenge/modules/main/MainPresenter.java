package com.granzotto.marcio.loadsmartchallenge.modules.main;

import java.lang.ref.WeakReference;

public class MainPresenter implements MainContracts.Presenter {

	private final WeakReference<MainContracts.View> view;

	MainPresenter(MainContracts.View view) {
		this.view = new WeakReference<>(view);
	}

	//region Presenter Contract

	@Override
	public void onCreate() {
		//TODO: create database if there's no cities
	}

	@Override
	public void onResume() {
		//TODO: update temperatures
	}

	@Override
	public void onDestroy() {
		this.view.clear();
	}

	//endregion
}
