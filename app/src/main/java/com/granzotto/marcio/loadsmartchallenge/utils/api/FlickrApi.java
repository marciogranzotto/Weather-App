package com.granzotto.marcio.loadsmartchallenge.utils.api;

import com.granzotto.marcio.loadsmartchallenge.models.FlickrResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface FlickrApi {

	@GET("rest/")
	Observable<FlickrResponse> fetchImageForCity(
			@Query("text") String cityName,
			@QueryMap HashMap<String, String> params
	);

}
