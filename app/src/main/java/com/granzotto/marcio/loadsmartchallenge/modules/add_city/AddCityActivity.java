package com.granzotto.marcio.loadsmartchallenge.modules.add_city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.granzotto.marcio.loadsmartchallenge.R;
import com.granzotto.marcio.loadsmartchallenge.modules.base.BaseActivity;

public class AddCityActivity extends BaseActivity implements AddCityContracts.View {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_city);
		setupView();
	}

	private void setupView() {
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar == null) return;
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		supportActionBar.setDisplayShowHomeEnabled(true);
	}
}
