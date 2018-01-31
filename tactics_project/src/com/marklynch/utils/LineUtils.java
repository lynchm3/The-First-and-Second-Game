package com.marklynch.utils;

import com.marklynch.Game;

import com.marklynch.utils.Color;

public class LineUtils {

	public static void drawLine(Color color, float x1, float y1, float x2, float y2, float lineWidth) {

		float dx = x2 - x1;
		float dy = y2 - y1;
		float dist = (float) Math.sqrt(dx * dx + dy * dy);
		float rad = (float) Math.atan2(dy, dx);

		Game.activeBatch.setColor(color);
		Game.activeBatch.draw(Game.quadTexture, x1, y1, dist, lineWidth, 0, 0, rad);
		Game.activeBatch.setColor(Color.WHITE);
	}

}
