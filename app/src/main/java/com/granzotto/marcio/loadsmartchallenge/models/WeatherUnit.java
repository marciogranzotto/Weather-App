package com.granzotto.marcio.loadsmartchallenge.models;

public enum WeatherUnit {

	CELSIUS("metric"), FAHRENHEIT("imperial"), KELVIN("default");

	String value;

	WeatherUnit(String unitName) {
		value = unitName;
	}

	@Override
	public String toString() {
		return value;
	}
}
