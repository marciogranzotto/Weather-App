package com.granzotto.marcio.loadsmartchallenge.utils.helpers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DensityHelper {

	public static int dipToPx(int dip, Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics));
	}
}
