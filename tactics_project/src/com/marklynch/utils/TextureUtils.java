package com.marklynch.utils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

import com.marklynch.Game;

public class TextureUtils {

	// master drawTexture method
	public static void drawTexture(Texture texture, float alpha, float x1,
			float x2, float y1, float y2, boolean inBounds, float boundsX1,
			float boundsX2, float boundsY1, float boundsY2, boolean backwards,
			boolean upsideDown) {

		float textureX1 = 0f;
		float vertexX1 = x1;
		float textureX2 = 1f;
		float vertexX2 = x2;
		float textureY1 = 0f;
		float vertexY1 = y1;
		float textureY2 = 1f;
		float vertexY2 = y2;

		if (inBounds == true) {

			float imageWidth = Math.abs(x2 - x1);
			float imageHeight = Math.abs(y2 - y1);

			// x1
			float outOfBoundsLeft = boundsX1 - x1;
			if (outOfBoundsLeft <= 0) {
				// completely in bounds
			} else if (outOfBoundsLeft >= imageWidth) {
				// completely out of bounds, don't draw anything
				return;
			} else {
				textureX1 = outOfBoundsLeft / imageWidth;
				vertexX1 = boundsX1;
			}

			// x2
			float outOfBoundsRight = x2 - boundsX2;
			if (outOfBoundsRight <= 0) {
				// completely in bounds
			} else if (outOfBoundsRight >= imageWidth) {
				// completely out of bounds, don't draw anything
				return;
			} else {
				textureX2 = 1f - (outOfBoundsRight / imageWidth);
				vertexX2 = boundsX2;
			}

			// x1
			float outOfBoundsTop = boundsY1 - y1;
			if (outOfBoundsTop <= 0) {
				// completely in bounds
			} else if (outOfBoundsTop >= imageHeight) {
				// completely out of bounds, don't draw anything
				return;
			} else {
				textureY1 = outOfBoundsTop / imageHeight;
				vertexY1 = boundsY1;
			}

			// x2
			float outOfBoundsBottom = y2 - boundsY2;
			if (outOfBoundsBottom <= 0) {
				// completely in bounds
			} else if (outOfBoundsBottom >= imageHeight) {
				// completely out of bounds, don't draw anything
				return;
			} else {
				textureY2 = 1f - (outOfBoundsBottom / imageHeight);
				vertexY2 = boundsY2;
			}
		}

		if (backwards) {
			textureX1 = 1f - textureX1;
			textureX2 = 1f - textureX2;
		}

		if (upsideDown) {
			textureX1 = 1f - textureX1;
			textureX2 = 1f - textureX2;
		}

		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);

		Game.batch.setColor(Color.WHITE);

		// draw some sprites... they will all be affected by our shaders
		// batch.draw(tex, 10, 10);
		Game.batch.draw(texture, vertexX1, vertexY1, vertexX2 - vertexX1,
				vertexY2 - vertexY1);

		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
		// texture.bind();
		// GL11.glBegin(GL11.GL_QUADS);
		//
		// GL11.glTexCoord2f(textureX1, textureY1);
		// GL11.glVertex2f(vertexX1, vertexY1);
		//
		// GL11.glTexCoord2f(textureX2, textureY1);
		// GL11.glVertex2f(vertexX2, vertexY1);
		//
		// GL11.glTexCoord2f(textureX2, textureY2);
		// GL11.glVertex2f(vertexX2, vertexY2);
		//
		// GL11.glTexCoord2f(textureX1, textureY2);
		// GL11.glVertex2f(vertexX1, vertexY2);
		//
		// GL11.glEnd();

	}

	public static void drawTexture(Texture texture, float x1, float x2,
			float y1, float y2) {
		drawTexture(texture, 1.0f, x1, x2, y1, y2, false, 0, 0, 0, 0, false,
				false);
	}

	public static void drawTexture(Texture texture, float alpha, float x1,
			float x2, float y1, float y2) {
		drawTexture(texture, alpha, x1, x2, y1, y2, false, 0, 0, 0, 0, false,
				false);
	}

	public static void drawTextureBackwards(Texture texture, float alpha,
			float x1, float x2, float y1, float y2) {
		drawTexture(texture, alpha, x1, x2, y1, y2, false, 0, 0, 0, 0, true,
				false);
	}

	public static void drawTextureupsideDown(Texture texture, float alpha,
			float x1, float x2, float y1, float y2) {
		drawTexture(texture, alpha, x1, x2, y1, y2, false, 0, 0, 0, 0, false,
				true);
	}

	public static void drawTextureWithinBounds(Texture texture, float alpha,
			float x1, float x2, float y1, float y2, float boundsX1,
			float boundsX2, float boundsY1, float boundsY2) {
		drawTexture(texture, alpha, x1, x2, y1, y2, true, boundsX1, boundsX2,
				boundsY1, boundsY2, false, false);
	}
}
