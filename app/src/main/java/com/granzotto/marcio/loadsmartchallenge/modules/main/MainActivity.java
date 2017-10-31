package com.granzotto.marcio.loadsmartchallenge.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.granzotto.marcio.loadsmartchallenge.R;
import com.granzotto.marcio.loadsmartchallenge.models.City;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;
import com.granzotto.marcio.loadsmartchallenge.modules.add_city.AddCityActivity;
import com.granzotto.marcio.loadsmartchallenge.modules.base.BaseActivity;
import com.granzotto.marcio.loadsmartchallenge.utils.adapters.CityAdapter;
import com.granzotto.marcio.loadsmartchallenge.utils.custom_views.CustomSwipeRefreshLayout;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.CityDBDataManager;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.WeatherApiDataManager;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContracts.View {

	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	@BindView(R.id.addButton)
	FloatingActionButton addButton;

	@BindView(R.id.swipeRefreshLayout)
	CustomSwipeRefreshLayout swipeRefreshLayout;

	//Ideally this should be injected using something like Dagger 2, but I did't had much time, sorry
	private MainContracts.Presenter presenter = new MainPresenter(
			this,
			new CityDBDataManager(),
			new WeatherApiDataManager(WeatherUnit.FAHRENHEIT)
	);

	private CityAdapter adapter;

	//region Lifecycle

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setupRecyclerView();
		presenter.onCreate();
	}

	@Override
	protected void onResume() {
		super.onResume();
		presenter.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.onDestroy();
	}

	@Override
	public void showLoadingDialog() {
		swipeRefreshLayout.setRefreshing(true);
	}

	@Override
	public void hideLoadingDialog() {
		swipeRefreshLayout.setRefreshing(false);
	}

	//endregion

	//region View Contract

	@Override
	public void showCities(List<City> cities, HashMap<String, Double> temperatures) {
		adapter.setCities(cities);
		adapter.setTemperatures(temperatures);
		adapter.notifyDataSetChanged();
	}

	//endregion

	//region Listeners

	@OnClick(R.id.addButton)
	protected void onAddButtonClicked() {
		Intent intent = new Intent(this, AddCityActivity.class);
		startActivity(intent);
	}

	//endregion

	//region Private

	private void setupRecyclerView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new CityAdapter();
		recyclerView.setAdapter(adapter);

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (dy > 0)
					addButton.hide();
				else if (dy < 0)
					addButton.show();
			}
		});

		swipeRefreshLayout.setOnRefreshListener(() -> presenter.onSwipeToRefreshTriggered());
	}

	//endregion
}