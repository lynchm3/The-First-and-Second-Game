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

	public static void drawTextureWithinBounds(Texture texture, float alpha,
			float imageX1, float imageX2, float imageY1, float imageY2,
			float boundsX1, float boundsX2, float boundsY1, float boundsY2) {

		float imageWidth = Math.abs(imageX2 - imageX1);
		float imageHeight = Math.abs(imageY2 - imageY1);

		// x1
		float textureX1 = 0f;
		float vertexX1 = imageX1;
		float outOfBoundsLeft = boundsX1 - imageX1;
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
		float textureX2 = 1f;
		float vertexX2 = imageX2;
		float outOfBoundsRight = imageX2 - boundsX2;
		if (outOfBoundsRight <= 0) {
			// completely in bounds
		} else if (outOfBoundsRight >= imageWidth) {
			// completely out of bounds, don't draw anything
			return;
		} else {
			textureX2 = 1f - (outOfBoundsRight / imageWidth);
			System.out.println("outOfBoundsRight = " + outOfBoundsRight);
			System.out.println("imageWidth = " + imageWidth);
			System.out.println("textureX2 = " + textureX2);
			vertexX2 = boundsX2;
		}

		// x1
		float textureY1 = 0f;
		float vertexY1 = imageY1;
		float outOfBoundsTop = boundsY1 - imageY1;
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
		float textureY2 = 1f;
		float vertexY2 = imageY2;
		float outOfBoundsBottom = imageY2 - boundsY2;
		if (outOfBoundsBottom <= 0) {
			// completely in bounds
		} else if (outOfBoundsBottom >= imageHeight) {
			// completely out of bounds, don't draw anything
			return;
		} else {
			textureY2 = 1f - (outOfBoundsBottom / imageHeight);
			vertexY2 = boundsY2;
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(textureX1, textureY1);
		GL11.glVertex2f(vertexX1, vertexY1);

		GL11.glTexCoord2f(textureX2, textureY1);
		GL11.glVertex2f(vertexX2, vertexY1);

		GL11.glTexCoord2f(textureX2, textureY2);
		GL11.glVertex2f(vertexX2, vertexY2);

		GL11.glTexCoord2f(textureX1, textureY2);
		GL11.glVertex2f(vertexX1, vertexY2);

		GL11.glEnd();

	}
}
