package com.granzotto.marcio.loadsmartchallenge.modules.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements BaseContracts.View {

	Dialog dialog;

	@Override
	public Context getActivityContext() {
		return this;
	}

	@Override
	public void showLoadingDialog() {
		//TODO
	}

	@Override
	public void hideLoadingDialog() {
		//TODO
	}

	@Override
	public void showErrorDialog(String message) {
		//TODO
	}
}
