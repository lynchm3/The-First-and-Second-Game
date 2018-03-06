package com.marklynch.level.constructs;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;

public class Path {

	public Path(Square... squares) {

		for (Square square : squares) {
			square.imageTexture = Square.STONE_TEXTURE;
			square.calculatePathCost();
			square.calculatePathCostForPlayer();
		}

	}

	public Path(int x1, int y1, int x2, int y2) {
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				Game.level.squares[i][j].imageTexture = Square.STONE_TEXTURE;
				Game.level.squares[i][j].calculatePathCost();
				Game.level.squares[i][j].calculatePathCostForPlayer();
			}
		}

	}

}
