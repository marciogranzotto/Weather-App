package com.granzotto.marcio.loadsmartchallenge.modules.add_city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.granzotto.marcio.loadsmartchallenge.R;
import com.granzotto.marcio.loadsmartchallenge.modules.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCityActivity extends BaseActivity implements AddCityContracts.View {

	private AddCityContracts.Presenter presenter = new AddCityPresenter(this);

	@BindView(R.id.cityEditText)
	TextView cityEditText;

	@BindView(R.id.stateEditText)
	TextView stateEditText;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_city);
		ButterKnife.bind(this);
		setupView();
		presenter.onCreate();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.onDestroy();
	}

	//region Private

	private void setupView() {
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar == null) return;
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		supportActionBar.setDisplayShowHomeEnabled(true);
	}

	//endregion

	//region View Contract

	@Override
	public void closeScreen() {
		this.finish();
	}

	//endregion

	//region Listeners

	@OnClick(R.id.saveButton)
	protected void onSaveButtonClicked() {
		presenter.onSaveButtonClicked(cityEditText.getText().toString(), stateEditText.getText().toString());
	}

	//endregion
}
