package com.granzotto.marcio.weatherapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class City extends RealmObject {

	@PrimaryKey
	private String id;
	private String name;
	private String stateName;
	private String imageUrl;

	public City() {
	}

	public City(String name, String stateName) {
		this.name = name;
		this.stateName = stateName;
	}

	public City(String id, String name, String stateName, String imageUrl) {
		this.id = id;
		this.name = name;
		this.stateName = stateName;
		this.imageUrl = imageUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
