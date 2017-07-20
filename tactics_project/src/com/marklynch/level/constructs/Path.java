package com.marklynch.level.constructs;

import com.marklynch.level.squares.Square;

public class Path {

	public Path(Square... squares) {

		for (Square square : squares) {
			square.imageTexture = Square.STONE_TEXTURE;
		}

	}

}
