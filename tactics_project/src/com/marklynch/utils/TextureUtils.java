package com.marklynch.utils;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class TextureUtils {

	public static void drawTexture(Texture texture, float x1, float x2,
			float y1, float y2) {
		drawTexture(texture, 1.0f, x1, x2, y1, y2);
	}

	public static void drawTexture(Texture texture, float alpha, float x1,
			float x2, float y1, float y2) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x1, y1);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x2, y1);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x2, y2);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x1, y2);
		GL11.glEnd();
	}

}
