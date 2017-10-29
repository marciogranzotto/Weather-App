package com.granzotto.marcio.loadsmartchallenge.modules.base;

public interface BaseContracts {

	interface View {
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
