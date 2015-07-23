package com.marklynch.utils;

import mdesl.graphics.Color;

import com.marklynch.Game;

public class QuadUtils {

	public static void drawQuad(Color color, float x1, float x2, float y1,
			float y2) {
		// GL11.glDisable(GL11.GL_TEXTURE_2D);
		// GL11.glColor4f(color.r, color.g, color.b, color.a);

		Game.batch.setColor(color);
		Game.batch.draw(Game.quadTexture, x1, y1, x2
				- x1, y2 - y1);
		// GL11.glBegin(GL11.GL_QUADS);
		// GL11.glVertex2f(x1, y1);
		// GL11.glVertex2f(x2, y1);
		// GL11.glVertex2f(x2, y2);
		// GL11.glVertex2f(x1, y2);
		// GL11.glEnd();
	}
}
