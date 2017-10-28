package com.granzotto.marcio.loadsmartchallenge.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.granzotto.marcio.loadsmartchallenge.R;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;
import com.granzotto.marcio.loadsmartchallenge.utils.WeatherApiDataManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.textView)
	TextView textView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		new WeatherApiDataManager(WeatherUnit.FAHRENHEIT)
				.fetchWeather("London")
				.subscribe(new Consumer<Double>() {
					@Override
					public void accept(Double temperature) throws Exception {
						textView.setText("Temp: " + temperature);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						Log.e("Error", "Error getting weather", throwable);
						textView.setText("Error!! Check log");
					}
				});
	}
}