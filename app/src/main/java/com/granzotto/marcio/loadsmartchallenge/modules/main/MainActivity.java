package com.granzotto.marcio.loadsmartchallenge.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.granzotto.marcio.loadsmartchallenge.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}