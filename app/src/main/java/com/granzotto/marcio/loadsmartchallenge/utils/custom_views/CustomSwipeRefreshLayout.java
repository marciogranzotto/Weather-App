package com.granzotto.marcio.loadsmartchallenge.utils.custom_views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.granzotto.marcio.loadsmartchallenge.R;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

	public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setColorSchemeResources(R.color.colorAccent);
	}
}
