package com.marklynch.utils;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import mdesl.graphics.Color;
import mdesl.graphics.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;

public class TextureUtils {

	// master drawTexture method
	public static void drawTexture(Texture texture, float alpha, float x1,
			float x2, float y1, float y2, boolean inBounds, float boundsX1,
			float boundsX2, float boundsY1, float boundsY2, boolean backwards,
			boolean upsideDown) {

		float imageWidth = Math.abs(x2 - x1);
		float imageHeight = Math.abs(y2 - y1);
		float textureX1 = 0f;
		float vertexX1 = x1;
		float textureX2 = imageWidth;
		float vertexX2 = x2;
		float textureY1 = 0f;
		float vertexY1 = y1;
		float textureY2 = imageHeight;
		float vertexY2 = y2;

		inBounds = false;
		// if (inBounds == true) {
		//
		// // x1
		// float outOfBoundsLeft = boundsX1 - x1;
		// if (outOfBoundsLeft <= 0) {
		// // completely in bounds
		// } else if (outOfBoundsLeft >= imageWidth) {
		// // completely out of bounds, don't draw anything
		// return;
		// } else {
		// textureX1 = outOfBoundsLeft;
		// vertexX1 = boundsX1;
		// }
		//
		// // x2
		// float outOfBoundsRight = x2 - boundsX2;
		// if (outOfBoundsRight <= 0) {
		// // completely in bounds
		// } else if (outOfBoundsRight >= imageWidth) {
		// // completely out of bounds, don't draw anything
		// return;
		// } else {
		// textureX2 = (imageWidth - outOfBoundsRight) - outOfBoundsLeft;
		// vertexX2 = boundsX2;
		// }
		//
		// // x1
		// float outOfBoundsTop = boundsY1 - y1;
		// if (outOfBoundsTop <= 0) {
		// // completely in bounds
		// } else if (outOfBoundsTop >= imageHeight) {
		// // completely out of bounds, don't draw anything
		// return;
		// } else {
		// textureY1 = outOfBoundsTop;
		// vertexY1 = boundsY1;
		// }
		//
		// // x2
		// float outOfBoundsBottom = y2 - boundsY2;
		// if (outOfBoundsBottom <= 0) {
		// // completely in bounds
		// } else if (outOfBoundsBottom >= imageHeight) {
		// // completely out of bounds, don't draw anything
		// return;
		// } else {
		// textureY2 = (imageHeight - outOfBoundsBottom) - outOfBoundsTop;
		// vertexY2 = boundsY2;
		// }
		// }
		//
		// if (backwards) {
		// textureX1 = 1f - textureX1;
		// textureX2 = 1f - textureX2;
		// }
		//
		// if (upsideDown) {
		// textureX1 = 1f - textureX1;
		// textureX2 = 1f - textureX2;
		// }

		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);

		Game.activeBatch.setColor(Color.WHITE);
		// Game.batch.setColor(1f, 1.0f, 1.0f, 0.1f);

		// draw some sprites... they will all be affected by our shaders
		// batch.draw(tex, 10, 10);
		// Game.batch.drawRegion(texture, textureX1, textureY1, textureX2,
		// textureY2, vertexX1, vertexY1, vertexX2 - vertexX1, vertexY2
		// - vertexY1);

		// bind normal map to texture unit 1
		glActiveTexture(GL_TEXTURE1);
		GameObject.grassNormalTexture.bind();

		// bind diffuse color to texture unit 0
		glActiveTexture(GL_TEXTURE0);
		texture.bind();

		// draw the texture unit 0 with our shader effect applied
		Game.activeBatch.draw(texture, vertexX1, vertexY1, vertexX2 - vertexX1,
				vertexY2 - vertexY1);

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
