package com.marklynch.utils;

public class FormattingUtils {

	public static String formatFloatRemoveUnneccessaryDigits(float f) {
		if (f == (long) f)
			return String.format("%d", (long) f);
		else
			return String.format("%s", f);
	}

}
