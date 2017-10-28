package com.granzotto.marcio.loadsmartchallenge.utils;


import android.util.Log;

import com.google.gson.JsonObject;
import com.granzotto.marcio.loadsmartchallenge.BuildConfig;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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

	public Observable<Double> fetchWeather(String cityName) {
		return getRetrofit().create(WeatherAPI.class)
				.fetchWeather(cityName, weatherUnit.toString(), BuildConfig.OpenWheatherApiKey)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnNext(new Consumer<JsonObject>() {
					@Override
					public void accept(JsonObject jsonObject) throws Exception {
						if (BuildConfig.DEBUG) {
							Log.d(TAG, jsonObject.toString());
						}
					}
				})
				.map(new Function<JsonObject, Double>() {
					@Override
					public Double apply(JsonObject jsonObject) throws Exception {
						return jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
					}
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
			HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
				@Override
				public void log(String message) {
					Log.d("HttpLoggingInterceptor", message);
				}
			});
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
