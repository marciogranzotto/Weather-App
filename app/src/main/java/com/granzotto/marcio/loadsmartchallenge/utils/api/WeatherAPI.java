package com.granzotto.marcio.loadsmartchallenge.utils.api;


import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

	@GET("weather/")
	Observable<JsonObject> fetchWeatherForCityName(
			@Query("q") String cityName,
			@Query("units") String unit,
			@Query("APPID") String ApiKey
	);

	@GET("group")
	Observable<JsonObject> fetchWeatherBatch(
			@Query("id") String ids,
			@Query("units") String unit,
			@Query("APPID") String ApiKey
	);

}
