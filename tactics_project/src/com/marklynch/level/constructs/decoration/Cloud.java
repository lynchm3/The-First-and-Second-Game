package com.marklynch.level.constructs.decoration;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.utils.Color;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Cloud extends Decoration {

	float x, y, width, height;
	Texture texture;

	public Cloud() {
		super();
		x = (float) (Math.random() * Level.squares.length * Game.SQUARE_WIDTH);
		y = (float) (Math.random() * Level.squares[0].length * Game.SQUARE_HEIGHT);
		texture = cloudTexture;
		width = cloudTexture.getWidth();
		height = cloudTexture.getHeight();
	}

	// @Override
	// public void draw1() {
	// }
	//
	// @Override
	// public void draw2() {
	//
	// }

	@Override
	public void draw3() {

		// 1. get list of squares this is draw nover
		float x1 = x;
		float y1 = y;
		float x2 = x + width;
		float y2 = y + height;

		int x1InSquares = (int) (x1 / Game.SQUARE_WIDTH) - 1;
		int y1InSquares = (int) (y1 / Game.SQUARE_HEIGHT) - 1;
		int x2InSquares = (int) (x2 / Game.SQUARE_WIDTH);
		int y2InSquares = (int) (y2 / Game.SQUARE_HEIGHT);

		if (x1InSquares < 0)
			x1InSquares = 0;

		if (y1InSquares < 0)
			y1InSquares = 0;

		if (x2InSquares >= Level.squares.length)
			x2InSquares = Level.squares.length - 1;

		if (y2InSquares >= Level.squares[0].length)
			y2InSquares = Level.squares[0].length - 1;

		for (int squareX = x1InSquares; squareX <= x2InSquares; squareX++) {
			for (int squareY = y1InSquares; squareY <= y2InSquares; squareY++) {

				Square squareCloudIsOver = Level.squares[squareX][squareY];
				if (squareCloudIsOver.visibleToPlayer && squareCloudIsOver.structureSquareIsIn == null) {

					TextureUtils.drawTextureWithinBounds(Decoration.cloudTexture, Level.shadowDarkness, x1, y1, x2, y2,
							squareCloudIsOver.xInGridPixels, squareCloudIsOver.yInGridPixels,
							squareCloudIsOver.xInGridPixels + Game.SQUARE_WIDTH,
							squareCloudIsOver.yInGridPixels + Game.SQUARE_HEIGHT, false, false, Color.WHITE);
					// TextureUtils.drawTexture(Decoration.cloudTexture, Level.shadowDarkness, x, y,
					// x + width, y + height,
					// Color.BLACK);
				}
			}
		}

	}

	// @Override
	// public void update(int delta) {
	//
	// }

	@Override
	public void updateRealtime(int delta) {
		x += delta * 0.1;
		if (x > Level.squares.length * Game.SQUARE_WIDTH) {
			x = -Decoration.cloudTexture.getWidth();
		}

	}

}
