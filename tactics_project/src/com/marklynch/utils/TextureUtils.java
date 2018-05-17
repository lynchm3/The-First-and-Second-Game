package com.marklynch.utils;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;

public class TextureUtils {

	static Color neutralColor = new Color(1f, 1f, 1f, 1f);

	public static boolean skipNormals = false;

	// master drawTexture method
	public static void drawTexture(Texture texture, float alpha, float x1, float y1, float x2, float y2,
			boolean inBounds, float boundsX1, float boundsY1, float boundsX2, float boundsY2, boolean backwards,
			boolean upsideDown, boolean inbounds) {
		drawTexture(texture, alpha, x1, y1, x2, y2, boundsX1, boundsY1, boundsX2, boundsY2, backwards, upsideDown,
				neutralColor, inbounds);

	}

	// master drawTexture method
	public static void drawTexture(Texture texture, float alpha, float x1, float y1, float x2, float y2, float boundsX1,
			float boundsY1, float boundsX2, float boundsY2, boolean backwards, boolean upsideDown, Color color,
			boolean inBounds) {

		if (texture == null)
			return;

		float vertexX1 = x1;
		float vertexY1 = y1;
		float vertexX2 = x2;
		float vertexY2 = y2;

		// float imageWidth = texture.getWidth();
		// float imageHeight = texture.getHeight();

		float textureX1 = 0f;
		float textureY1 = 0f;
		float textureX2 = texture.getWidth();
		float textureY2 = texture.getHeight();

		// float bottom = y1

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
				textureX1 = outOfBoundsLeft;
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
				textureX2 = texture.getWidth() - (outOfBoundsRight);
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
				textureY1 = outOfBoundsTop;
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
				textureY2 = texture.getHeight() - (outOfBoundsBottom);
				vertexY2 = boundsY2;
			}
		}

		// Colors everything white
		// System.out.println("color.red(); = " + color.red());
		// Game.activeBatch.setColor(255, 255, 255, alpha);

		// all green
		// Game.activeBatch.setColor(0, 255, 0, alpha);

		// Colors everything black
		// Game.activeBatch.setColor(0, 0, 0, 1); // black

		// Gives all their proper color
		// Game.activeBatch.setColor(Color.WHITE);

		// neutral - 1,1,1
		// color.System.out.println("neutralColor = " + neutralColor);

		// Gives proper color... i'm so fucking confused
		Game.activeBatch.setColor(color.r, color.b, color.g, alpha);// gives all
																	// proper
																	// color

		glActiveTexture(GL_TEXTURE1);
		if (!skipNormals) {
			GameObject.grassNormalTexture.bind();
		} else {
			GameObject.skipNormalTexture.bind();
		}
		// bind diffuse color to texture unit 0
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		if (inBounds == true) {

			if (upsideDown) {

				float srcY = (texture.getHeight() - textureY1);
				float srcHeight = -(textureY2 - textureY1);

				Game.activeBatch.drawRegion(texture, textureX1, srcY, textureX2 - textureX1, srcHeight, vertexX1,
						vertexY1, vertexX2 - vertexX1, vertexY2 - vertexY1);
			} else {
				Game.activeBatch.drawRegion(texture, textureX1, textureY1, textureX2 - textureX1,
						(textureY2 - textureY1), vertexX1, vertexY1, vertexX2 - vertexX1, vertexY2 - vertexY1);
			}
		} else {
			if (backwards) {
				Game.activeBatch.draw(texture, vertexX2, vertexY1, -(vertexX2 - vertexX1), vertexY2 - vertexY1);

			} else {
				Game.activeBatch.draw(texture, vertexX1, vertexY1, vertexX2 - vertexX1, vertexY2 - vertexY1);
			}

		}

		Game.activeBatch.setColor(1, 1, 1, 1);

	}

	// master drawTexture method
	public static void drawTexture(Texture texture, float x1, float y1, float x2, float y2, float u1, float v1,
			float u2, float v2) {

		float vertexX1 = x1;
		float vertexX2 = x2;
		float vertexY1 = y1;
		float vertexY2 = y2;

		Game.activeBatch.setColor(1, 1, 1, 1);
		// Game.batch.setColor(1f, 1.0f, 1.0f, 0.1f);

		// draw some sprites... they will all be affected by our shaders
		// batch.draw(tex, 10, 10);
		// Game.batch.drawRegion(texture, textureX1, textureY1, textureX2,
		// textureY2, vertexX1, vertexY1, vertexX2 - vertexX1, vertexY2
		// - vertexY1);

		// bind normal map to texture unit 1
		glActiveTexture(GL_TEXTURE1);
		if (!skipNormals) {
			GameObject.grassNormalTexture.bind();
		} else {
			GameObject.skipNormalTexture.bind();
		}
		// bind diffuse color to texture unit 0
		glActiveTexture(GL_TEXTURE0);
		texture.bind();

		// draw the texture unit 0 with our shader effect applied

		Game.activeBatch.draw(texture, vertexX1, vertexY1, vertexX2 - vertexX1, vertexY2 - vertexY1, u1, v1, u2, v2);

		Game.activeBatch.setColor(1, 1, 1, 1);

	}

	public static void drawTexture(Texture texture, float x1, float y1, float x2, float y2) {
		drawTexture(texture, 1.0f, x1, y1, x2, y2, 0, 0, 0, 0, false, false, neutralColor, false);
	}

	public static void drawTexture(Texture texture, float x1, float y1, float x2, float y2, Color color) {
		drawTexture(texture, 1.0f, x1, y1, x2, y2, 0, 0, 0, 0, false, false, color, false);
	}

	public static void drawTexture(Texture texture, float alpha, float x1, float y1, float x2, float y2, Color color) {
		drawTexture(texture, alpha, x1, y1, x2, y2, 0, 0, 0, 0, false, false, color, false);
	}

	public static void drawTexture(Texture texture, float alpha, float x1, float y1, float x2, float y2) {
		drawTexture(texture, alpha, x1, y1, x2, y2, false, 0, 0, 0, 0, false, false, false);
	}

	public static void drawTexture(Texture texture, float alpha, float x1, float y1, float x2, float y2,
			boolean backwards) {
		drawTexture(texture, alpha, x1, y1, x2, y2, false, 0, 0, 0, 0, backwards, false, false);
	}

	public static void drawTextureBackwards(Texture texture, float alpha, float x1, float y1, float x2, float y2) {
		drawTexture(texture, alpha, x1, y1, x2, y2, false, 0, 0, 0, 0, true, false, false);
	}

	public static void drawTextureupsideDown(Texture texture, float alpha, float x1, float y1, float x2, float y2) {
		drawTexture(texture, alpha, x1, y1, x2, y2, false, 0, 0, 0, 0, false, true, false);
	}

	public static void drawTextureWithinBounds(Texture texture, float alpha, float x1, float y1, float x2, float y2,
			float boundsX1, float boundsY1, float boundsX2, float boundsY2, boolean backwards, boolean upsideDown) {
		drawTexture(texture, alpha, x1, y1, x2, y2, true, boundsX1, boundsY1, boundsX2, boundsY2, backwards, upsideDown,
				true);
	}
}
