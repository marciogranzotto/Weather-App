package com.granzotto.marcio.loadsmartchallenge.utils;


import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.granzotto.marcio.loadsmartchallenge.BuildConfig;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiDataManager {

	private static String TAG = "WeatherApi";

	private WeatherUnit weatherUnit;
	private Retrofit retrofit = null;

	public WeatherApiDataManager(WeatherUnit weatherUnit) {
		this.weatherUnit = weatherUnit;
	}

	//MARK: Public methods

	public Observable<Long> fetchCityId(String cityName) {
		return getRetrofit().create(WeatherAPI.class)
				.fetchWeatherForCityName(cityName, weatherUnit.toString(), BuildConfig.OpenWheatherApiKey)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.map(jsonObject -> jsonObject.get("id").getAsLong());

	}

	public Observable<HashMap<String, Double>> fetchCurrentWeather(List<String> cityIds) {
		return getRetrofit().create(WeatherAPI.class)
				.fetchWeatherBatch(StringListHelper.toCommaSeparatedString(cityIds), weatherUnit.toString(), BuildConfig.OpenWheatherApiKey)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.map(jsonObject -> {
					HashMap<String, Double> map = new HashMap<>();
					for (JsonElement element : jsonObject.getAsJsonArray("list")) {
						JsonObject obj = element.getAsJsonObject();
						map.put(obj.get("id").getAsString(), obj.getAsJsonObject("main").get("temp").getAsDouble());
					}
					return map;
				});
	}

	//MARK: Private methods


	private Retrofit getRetrofit() {
		if (retrofit == null) {
			retrofit = buildRetrofit();
		}
		return retrofit;
	}

	private Retrofit buildRetrofit() {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		if (BuildConfig.DEBUG) {
			HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("HttpLoggingInterceptor", message));
			httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			clientBuilder.addNetworkInterceptor(httpLoggingInterceptor);
		}

		return new Retrofit.Builder()
				.baseUrl("http://api.openweathermap.org/data/2.5/")
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.client(clientBuilder.build())
				.build();
	}

}
