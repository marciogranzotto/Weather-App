package com.granzotto.marcio.weatherapp.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDialog;

import com.granzotto.marcio.weatherapp.BuildConfig;
import com.granzotto.marcio.weatherapp.R;
import com.granzotto.marcio.weatherapp.utils.helpers.DensityHelper;

public class LoadingDialog extends AppCompatDialog {


	public LoadingDialog(Context context) {
		super(context, R.style.ProgressDialog);
	}

	public static Dialog show(Context context) {
		LoadingDialog dialog = new LoadingDialog(context);
		dialog.setContentView(R.layout.loading_dialog);

		if (BuildConfig.VERSION_CODE < Build.VERSION_CODES.LOLLIPOP) { //elevation is not supported by default on pre-lollipop devices
			ViewCompat.setElevation(dialog.findViewById(R.id.background), DensityHelper.dipToPx(8, dialog.getContext()));
		}

		dialog.show();
		return dialog;
	}
}