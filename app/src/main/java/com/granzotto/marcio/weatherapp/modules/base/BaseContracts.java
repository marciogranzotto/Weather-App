package com.granzotto.marcio.weatherapp.modules.base;

import android.content.Context;

public interface BaseContracts {

	interface View {
		Context getActivityContext();

		void showLoadingDialog();

		void hideLoadingDialog();

		void showErrorDialog(String message);
	}

	interface Presenter {
		void onCreate();

		default void onResume() { //optional method
		}

		void onDestroy();
	}

}
