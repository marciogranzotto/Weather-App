package com.granzotto.marcio.loadsmartchallenge.utils.helpers;

import java.util.List;

public class StringListHelper {

	public static String toCommaSeparatedString(List<String> list) {
		if (list.isEmpty()) return "";
		StringBuilder text = new StringBuilder(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			text.append(",").append(list.get(i));
		}
		return text.toString();
	}

}
