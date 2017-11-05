package com.granzotto.marcio.loadsmartchallenge;

import android.content.Context;

import com.granzotto.marcio.loadsmartchallenge.modules.add_city.AddCityContracts;
import com.granzotto.marcio.loadsmartchallenge.modules.add_city.AddCityPresenter;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.CityDBDataManager;
import com.granzotto.marcio.loadsmartchallenge.utils.datamanagers.WeatherApiDataManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
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
public class AddCityPresenterTest {

	@Rule
	public PowerMockRule rule = new PowerMockRule();

	@Mock
	private AddCityContracts.View view;
	@Mock
	private CityDBDataManager dbDataManager;
	@Mock
	private WeatherApiDataManager weatherDataManager;

	private AddCityPresenter presenter;

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

		presenter = new AddCityPresenter(view, weatherDataManager, dbDataManager);
	}

}