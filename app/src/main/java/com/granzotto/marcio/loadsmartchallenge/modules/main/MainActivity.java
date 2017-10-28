package com.granzotto.marcio.loadsmartchallenge.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.granzotto.marcio.loadsmartchallenge.R;
import com.granzotto.marcio.loadsmartchallenge.models.WeatherUnit;
import com.granzotto.marcio.loadsmartchallenge.utils.WeatherApiDataManager;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

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

		//TODO: remove mock data
		new WeatherApiDataManager(WeatherUnit.FAHRENHEIT)
				.fetchCurrentWeather(Arrays.asList("524901", "703448", "2643743"))
				.subscribe(response -> {
					StringBuilder text = new StringBuilder();
					for (String key : response.keySet()) {
						text.append(key).append("\t").append(response.get(key)).append("\n");
					}
					textView.setText(text.toString());
				});
	}
}