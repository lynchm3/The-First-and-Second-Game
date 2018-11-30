package com.marklynch.level.constructs;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;

public class BodyOfWater {

	public BodyOfWater(Square... squares) {
		for (Square square : squares) {
			square.setFloorImageTexture(Square.WATER_TEXTURE);
			if (!square.inventory.contains(Templates.WATER_BODY.getClass()))
				square.inventory.add(Templates.WATER_BODY.makeCopy(null, null));
		}
	}

	public BodyOfWater(int x1, int y1, int x2, int y2) {
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (!Game.level.squares[i][j].inventory.contains(Templates.WATER_BODY.getClass())) {
					Game.level.squares[i][j].setFloorImageTexture(Square.WATER_TEXTURE);
					Game.level.squares[i][j].inventory.add(Templates.WATER_BODY.makeCopy(null, null));
				}
			}
		}
	}
}