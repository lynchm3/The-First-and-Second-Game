package com.marklynch.utils;

import com.marklynch.tactics.objects.level.Square;

public class ArrayUtils {

	public static boolean inBounds(Square[][] array, int index1, int index2) {
		return (index1 >= 0) && (index1 < array.length) && (index2 >= 0)
				&& (index2 < array[index1].length);
	}
}
