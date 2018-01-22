package com.marklynch.utils;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class TextureUtils {

	public static boolean skipNormals = false;

	// master drawTexture method
	public static void drawTexture(Texture texture, float alpha, float x1, float y1, float x2, float y2,
			boolean inBounds, float boundsX1, float boundsY1, float boundsX2, float boundsY2, boolean backwards,
			boolean upsideDown, boolean inbounds) {
		drawTexture(texture, alpha, x1, y1, x2, y2, boundsX1, boundsY1, boundsX2, boundsY2, backwards, Color.WHITE,
				inbounds);

	}

	// master drawTexture method
	public static void drawTexture(Texture texture, float alpha, float x1, float y1, float x2, float y2, float boundsX1,
			float boundsY1, float boundsX2, float boundsY2, boolean backwards, Color color, boolean inBounds) {

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

		if (inBounds == true) {
			System.out.println("inBounds == true");

			float imageWidth = Math.abs(x2 - x1);
			float imageHeight = Math.abs(y2 - y1);

			// x1
			float outOfBoundsLeft = boundsX1 - x1;
			if (outOfBoundsLeft <= 0) {
				// completely in bounds
				System.out.println("return a1");
			} else if (outOfBoundsLeft >= imageWidth) {
				// completely out of bounds, don't draw anything
				System.out.println("return a2");
				return;
			} else {
				System.out.println("return a3");
				textureX1 = outOfBoundsLeft;
				vertexX1 = boundsX1;
			}

			// x2
			float outOfBoundsRight = x2 - boundsX2;
			if (outOfBoundsRight <= 0) {
				// completely in bounds
				System.out.println("return b1");
			} else if (outOfBoundsRight >= imageWidth) {
				// completely out of bounds, don't draw anything
				System.out.println("return b2");
				return;
			} else {
				textureX2 = texture.getWidth() - (outOfBoundsRight);
				System.out.println("return b3");
				vertexX2 = boundsX2;
			}

			// x1
			float outOfBoundsTop = boundsY1 - y1;
			if (outOfBoundsTop <= 0) {
				// completely in bounds
				System.out.println("return c1");
			} else if (outOfBoundsTop >= imageHeight) {
				// completely out of bounds, don't draw anything
				System.out.println("return c2");
				return;
			} else {
				System.out.println("return c3");
				textureY1 = outOfBoundsTop;
				vertexY1 = boundsY1;
			}

			// x2
			float outOfBoundsBottom = y2 - boundsY2;
			if (outOfBoundsBottom <= 0) {
				// completely in bounds
				System.out.println("return d1");
			} else if (outOfBoundsBottom >= imageHeight) {
				// completely out of bounds, don't draw anything
				System.out.println("return d2");
				return;
			} else {
				System.out.println("return d3");
				textureY2 = texture.getHeight() - (outOfBoundsBottom);
				vertexY2 = boundsY2;
			}
		}

		color.a = alpha;
		Game.activeBatch.setColor(color);

		glActiveTexture(GL_TEXTURE1);
		if (!skipNormals) {
			GameObject.grassNormalTexture.bind();
		} else {
			GameObject.skipNormalTexture.bind();
		}
		// bind diffuse color to texture unit 0
		glActiveTexture(GL_TEXTURE0);
		texture.bind();

		// Game.activeBatch.drawRegion(texture, textureX1, textureY1, textureX2,
		// textureY2, vertexX1, vertexY1,
		// vertexX2 - vertexX1, vertexY2 - vertexY1);

		if (backwards) {
			System.out.println("backwards = true");
			Game.activeBatch.draw(texture, vertexX2, vertexY1, -(vertexX2 - vertexX1), vertexY2 - vertexY1);

		} else {
			if (inBounds == true) {

				System.out.println("vertexX1 = " + vertexX1);
				System.out.println("vertexY1 = " + vertexY1);
				System.out.println("vertexX2 - vertexX1 = " + (vertexX2 - vertexX1));
				System.out.println("vertexY2 - vertexY1 = " + (vertexY2 - vertexY1));

				System.out.println("textureX1 = " + textureX1);
				System.out.println("textureY1 = " + textureY1);
				System.out.println("textureX2 = " + textureX2);
				System.out.println("textureY2 = " + textureY2);

				Game.activeBatch.drawRegion(texture, textureX1, textureY1, textureX2 - textureX1, textureY2 - textureY1,
						vertexX1, vertexY1, vertexX2 - vertexX1, vertexY2 - vertexY1);
			} else {
				Game.activeBatch.draw(texture, vertexX1, vertexY1, vertexX2 - vertexX1, vertexY2 - vertexY1);

			}

			// Game.activeBatch.draw(texture, vertexX1, vertexY1, vertexX2 -
			// vertexX1, vertexY2 - vertexY1, textureX1,
			// textureY1, 0.5f, 1f);

		}

		Color whiteWith1Alpha = new Color(Color.WHITE);
		whiteWith1Alpha.a = 1;
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
		drawTexture(texture, 1.0f, x1, y1, x2, y2, 0, 0, 0, 0, false, Color.WHITE, false);
	}

	public static void drawTexture(Texture texture, float x1, float y1, float x2, float y2, Color color) {
		drawTexture(texture, 1.0f, x1, y1, x2, y2, 0, 0, 0, 0, false, color, false);
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
			float boundsX1, float boundsY1, float boundsX2, float boundsY2) {
		drawTexture(texture, alpha, x1, y1, x2, y2, true, boundsX1, boundsY1, boundsX2, boundsY2, false, false, true);
	}
}
