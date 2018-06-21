package com.marklynch.level.constructs.decoration;

import com.marklynch.Game;
import com.marklynch.level.Level;
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
		TextureUtils.drawTexture(Decoration.cloudTexture, Level.shadowDarkness, x, y, x + width, y + height,
				Color.BLACK);

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
