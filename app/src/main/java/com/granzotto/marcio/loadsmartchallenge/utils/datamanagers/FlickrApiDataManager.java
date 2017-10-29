package com.granzotto.marcio.loadsmartchallenge.utils.datamanagers;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.granzotto.marcio.loadsmartchallenge.BuildConfig;
import com.granzotto.marcio.loadsmartchallenge.utils.api.FlickrApi;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrApiDataManager {

	//region Singleton boiler plate

	private static final FlickrApiDataManager ourInstance = new FlickrApiDataManager();

	public static FlickrApiDataManager getInstance() {
		return ourInstance;
	}

	private FlickrApiDataManager() {
	}

	//endregion

	//region Static

	private static HashMap<String, String> QUERY_MAP;

	static {
		QUERY_MAP = new HashMap<>();
		QUERY_MAP.put("method", "flickr.photos.search");
		QUERY_MAP.put("api_key", BuildConfig.FlickrApiKey);
		QUERY_MAP.put("format", "json");
		QUERY_MAP.put("nojsoncallback", "1");
		QUERY_MAP.put("sort", "relevance");
	}

	//endregion

	private Retrofit retrofit;

	//region: Public methods

	public Observable<String> fetchImageUrl(String cityName) {
		return getRetrofit().create(FlickrApi.class)
				.fetchImageForCity(cityName + " city", QUERY_MAP) //appending "city" to the city name gives better results
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.map(response -> response.getPhotos().getPhoto().get(0).toUrlString()); //gets the first result
	}

	//endregion

	//region: Private methods


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

		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();

		return new Retrofit.Builder()
				.baseUrl("https://api.flickr.com/services/")
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(clientBuilder.build())
				.build();
	}

	//endregion
}
