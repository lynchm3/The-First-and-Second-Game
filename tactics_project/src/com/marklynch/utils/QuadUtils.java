package com.marklynch.utils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

import com.marklynch.Game;

public class QuadUtils {

	public static void drawQuad(Color color, float x1, float x2, float y1,
			float y2) {
		// GL11.glDisable(GL11.GL_TEXTURE_2D);
		// GL11.glColor4f(color.r, color.g, color.b, color.a);

		Game.activeBatch.setColor(color);
		Game.activeBatch.draw(Game.quadTexture, x1, y1, x2 - x1, y2 - y1);

		// GL11.glBegin(GL11.GL_QUADS);
		// GL11.glVertex2f(x1, y1);
		// GL11.glVertex2f(x2, y1);
		// GL11.glVertex2f(x2, y2);
		// GL11.glVertex2f(x1, y2);
		// GL11.glEnd();
	}

	public static void drawQuad(Texture texture, float x1, float x2, float x3,
			float x4, float y1, float y2, float y3, float y4, float u1,
			float u2, float u3, float u4, float v1, float v2, float v3, float v4) {
		// GL11.glDisable(GL11.GL_TEXTURE_2D);
		// GL11.glColor4f(color.r, color.g, color.b, color.a);

		// Look at source for activebatch.draw
		// Looks like it draws triangles

		Game.activeBatch.checkFlush(texture);
		final float r = Game.activeBatch.color.r;
		final float g = Game.activeBatch.color.g;
		final float b = Game.activeBatch.color.b;
		final float a = Game.activeBatch.color.a;

		// float x1, y1, x2, y2, x3;

		// top left, top right, bottom left
		// Game.activeBatch.vertex(x1, y1, r, g, b, a, u1, v1);
		// Game.activeBatch.vertex(x2, y2, r, g, b, a, u2, v2);
		// Game.activeBatch.vertex(x3, y3, r, g, b, a, u3, v3);

		// top left, top right, bottom left
		Game.activeBatch.vertex(x1, y1, r, g, b, a, u1, v1);
		Game.activeBatch.vertex(x2, y2, r, g, b, a, u2, v2);
		Game.activeBatch.vertex(x4, y4, r, g, b, a, u4, v4);

		// top right, bottom right, bottom left
		Game.activeBatch.vertex(x2, y2, r, g, b, a, u2, v2);
		Game.activeBatch.vertex(x3, y3, r, g, b, a, u3, v3);
		Game.activeBatch.vertex(x4, y4, r, g, b, a, u4, v4);

		// GL11.glBegin(GL11.GL_QUADS);
		// GL11.glVertex2f(x1, y1);
		// GL11.glVertex2f(x2, y1);
		// GL11.glVertex2f(x2, y2);
		// GL11.glVertex2f(x1, y2);
		// GL11.glEnd();
	}
}
