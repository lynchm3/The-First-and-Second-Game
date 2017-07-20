package com.marklynch.ai.utils;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class UtilFind {

	public static GameObject findNearest(Class clazz, int roughMaxDistance, Square sourceSquare) {

		GameObject result = null;

		int xMin, xMax, yMin, yMax, xSrc, ySrc;

		xSrc = sourceSquare.xInGrid;
		xMin = sourceSquare.xInGrid - roughMaxDistance;
		if (xMin < 0)
			xMin = 0;
		xMax = sourceSquare.xInGrid + roughMaxDistance;
		if (xMax >= Game.level.squares.length)
			xMax = Game.level.squares.length - 1;
		ySrc = sourceSquare.yInGrid;
		yMin = sourceSquare.yInGrid - roughMaxDistance;
		if (yMin < 0)
			yMin = 0;
		yMax = sourceSquare.yInGrid + roughMaxDistance;
		if (yMax >= Game.level.squares.length)
			yMax = Game.level.squares[0].length - 1;

		int indexX;
		int indexY;

		// for (int i = 1; i < roughMaxDistance; i++) {
		// indexX = xSrc - i;
		// if (xSrc - i >= minX) {
		//
		// }
		// }

		// for (int i = xMin; i <= xMax; i++) {
		// for (int j = yMin; j <= yMax; j++) {
		// if (Game.level.squares[i][j].inventory.contains(clazz)) {
		//
		// }
		// }
		// }
		//
		// for (GameObject gameObject : Game.level.inanimateObjects) {
		//
		// }

		return null;

	}

}
