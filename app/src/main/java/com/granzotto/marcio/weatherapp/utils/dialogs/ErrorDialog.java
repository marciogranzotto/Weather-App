package com.granzotto.marcio.weatherapp.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.granzotto.marcio.weatherapp.R;

public class ErrorDialog extends AlertDialog {


	public ErrorDialog(Context context) {
		super(context);
	}

	public static Dialog show(String message, Context context) {
		ErrorDialog dialog = new ErrorDialog(context);
		dialog.setTitle(R.string.error);
		if (message == null) {
			message = context.getString(R.string.default_error);
		}
		dialog.setButton(BUTTON_POSITIVE, context.getString(R.string.ok), (dialogInterface, i) -> {
			dialog.dismiss();
		});
		dialog.setMessage(message);
		dialog.show();
		return dialog;
	}
}