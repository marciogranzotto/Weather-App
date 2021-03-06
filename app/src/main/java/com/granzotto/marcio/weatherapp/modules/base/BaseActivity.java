package com.granzotto.marcio.weatherapp.modules.base;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.granzotto.marcio.weatherapp.utils.dialogs.ErrorDialog;
import com.granzotto.marcio.weatherapp.utils.dialogs.LoadingDialog;

public class BaseActivity extends AppCompatActivity implements BaseContracts.View {

	Dialog dialog;

	@Override
	public Context getActivityContext() {
		return this;
	}

	@Override
	public void showLoadingDialog() {
		dialog = LoadingDialog.show(this);
	}

	@Override
	public void hideLoadingDialog() {
		if (dialog == null) return;
		dialog.dismiss();
	}

	@Override
	public void showErrorDialog(String message) {
		if (dialog != null) dialog.dismiss();
		dialog = ErrorDialog.show(message, this);
	}
}
