package com.granzotto.marcio.weatherapp.models;

public class FlickrPhoto {

	/*
	"id": "11723894133",
	"owner": "35275893@N04",
	"secret": "c294658495",
	"server": "5482",
	"farm": 6,
	"title": "San Francisco Skyline",
	"ispublic": 1,
	"isfriend": 0,
	"isfamily": 0
	 */

	private String id;
	private String secret;
	private String server;
	private int farm;

	//region getters and setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getFarm() {
		return farm;
	}

	public void setFarm(int farm) {
		this.farm = farm;
	}


	//endregion

	//region helper methods

	public String toUrlString() {
		return "https://farm"
				+ getFarm()
				+ ".staticflickr.com/"
				+ getServer()
				+ "/" + getId()
				+ "_" + getSecret() + ".jpg";
	}

	//endregion
}
