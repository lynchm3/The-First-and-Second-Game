package com.marklynch.utils;

import com.marklynch.level.Square;

public class ArrayUtils {

	public static boolean inBounds(Square[][] array, float index1, float index2) {
		return (index1 >= 0) && (index1 < array.length) && (index2 >= 0) && (index2 < array[(int) index1].length);
	}
}
