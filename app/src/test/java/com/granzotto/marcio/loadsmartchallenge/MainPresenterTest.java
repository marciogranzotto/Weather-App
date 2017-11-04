package com.granzotto.marcio.loadsmartchallenge;

import android.content.Context;

import com.granzotto.marcio.loadsmartchallenge.models.City;
import com.granzotto.marcio.loadsmartchallenge.modules.main.MainContracts;
import com.granzotto.marcio.loadsmartchallenge.modules.main.MainPresenter;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.CityDBDataManager;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.WeatherApiDataManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmConfiguration.class, RealmQuery.class, RealmResults.class, RealmCore.class, RealmLog.class})
public class MainPresenterTest {

	@Rule
	public PowerMockRule rule = new PowerMockRule();

	@Mock
	private MainContracts.View view;
	@Mock
	private CityDBDataManager dbDataManager;
	@Mock
	private WeatherApiDataManager weatherDataManager;

	private MainPresenter presenter;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockStatic(RealmCore.class);
		mockStatic(RealmLog.class);
		mockStatic(Realm.class);
		mockStatic(RealmConfiguration.class);
		Realm.init(RuntimeEnvironment.application);

		final Realm mockRealm = mock(Realm.class);
		final RealmConfiguration mockRealmConfig = mock(RealmConfiguration.class);

		doNothing().when(RealmCore.class);
		RealmCore.loadLibrary(any(Context.class));

		whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);
		when(Realm.getDefaultInstance()).thenReturn(mockRealm);

		presenter = new MainPresenter(view, dbDataManager, weatherDataManager);
	}

	@Test
	public void fetchWeatherShouldShowInView() {

		City city = new City("1234", "New York", "NY", "www.google.com");
		List<City> cities = Collections.singletonList(city);

		List<String> ids = Collections.singletonList(city.getId());
		HashMap<String, Double> response = new HashMap<>();
		response.put(city.getId(), 72.3);
		when(weatherDataManager.fetchCurrentWeather(ids))
				.thenReturn(Observable.just(response));

		presenter.setCities(cities);

		presenter.onSwipeToRefreshTriggered();

		assertEquals(presenter.getWeatherMap().get(city.getId()), new Double(72.3));
		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view, times(1)).hideLoadingDialog();
		inOrder.verify(view, times(1)).showCities(cities, presenter.getWeatherMap());
		verify(view, never()).showErrorDialog(null);
	}

	@Test
	public void fetchWeatherErrorShouldShowErrorDialog() {
		Exception exception = new Exception("Error message");

		when(weatherDataManager.fetchCurrentWeather(new ArrayList<>()))
				.thenReturn(Observable.error(exception));

		presenter.onSwipeToRefreshTriggered();
		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view, times(1)).showErrorDialog(exception.getMessage());
		verify(view, never()).showCities(presenter.getCities(), presenter.getWeatherMap());
	}

	@Test
	public void fetchCitiesShouldShowInView() {
		City city = new City("1234", "New York", "NY", "www.google.com");
		List<City> cities = Collections.singletonList(city);

		when(dbDataManager.fetchCities())
				.thenReturn(Observable.just(cities));

		List<String> ids = Collections.singletonList(city.getId());
		HashMap<String, Double> response = new HashMap<>();
		when(weatherDataManager.fetchCurrentWeather(ids))
				.thenReturn(Observable.just(response));

		presenter.onResume();

		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view, times(1)).showCities(cities, presenter.getWeatherMap());
		inOrder.verify(view, times(1)).hideLoadingDialog();
		inOrder.verify(view, times(1)).showCities(cities, presenter.getWeatherMap());
		verify(view, never()).showErrorDialog(null);
	}

	@Test
	public void fetchCitiesErrorShouldShowError() {
		Exception exception = new Exception("Error message");

		when(dbDataManager.fetchCities())
				.thenReturn(Observable.error(exception));

		presenter.onResume();
		InOrder inOrder = Mockito.inOrder(view);
		inOrder.verify(view, times(1)).showErrorDialog(exception.getMessage());
		verify(view, never()).showCities(presenter.getCities(), presenter.getWeatherMap());
	}

}
