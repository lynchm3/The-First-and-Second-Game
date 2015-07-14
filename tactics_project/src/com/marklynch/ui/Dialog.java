package com.marklynch.ui;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.utils.TextureUtils;

public class Dialog {

	public Square reference;
	public float width;
	public float height;

	// background
	public String backgroundImagePath = "";
	public Texture backgroundImageTexture = null;

	Level level;

	public Dialog(Square reference, float width, float height,
			String backgroundImagePath, String fontPath, Level level) {
		super();
		this.reference = reference;
		this.width = width;
		this.height = height;
		this.backgroundImagePath = backgroundImagePath;
		this.backgroundImageTexture = getGlobalImage(backgroundImagePath);
		this.level = level;
	}

	public void draw() {
		int positionYInPixels = (int) ((reference.y + 1) * Game.zoom
				* Game.SQUARE_HEIGHT - (Game.windowHeight * Game.zoom) / 2
				+ Game.dragY * Game.zoom + Game.windowHeight / 2);

		int positionXInPixels = (int) ((reference.x + 1) * Game.zoom
				* Game.SQUARE_WIDTH - (Game.windowWidth * Game.zoom) / 2
				+ Game.dragX * Game.zoom + Game.windowWidth / 2);

		// background
		// this.backgroundImageTexture.bind();

		// set the color of the quad (R,G,B,A)
		// GL11.glColor3f(0.5f, 0.5f, 1.0f);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glLineWidth(10.0f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(positionXInPixels + 5, positionYInPixels + 5);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(positionXInPixels - 64 * Game.zoom,
				positionYInPixels - 64 * Game.zoom);
		GL11.glEnd();

		// GL11.glLineWidth(1.0f);
		// drawCircle(10);

		TextureUtils.drawTexture(this.backgroundImageTexture,
				positionXInPixels, positionXInPixels + width,
				positionYInPixels, positionYInPixels + height);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);

		String[] strings = reference.getDetails();

		int i = 0;
		for (String string : strings) {
			level.font12.drawString(positionXInPixels + 10, positionYInPixels
					+ 20 + i, string, Color.black);
			i += 20;
		}

		GL11.glColor3f(1.0f, 1.0f, 1.0f);

	}

	void drawCircle(float radius) {
		GL11.glBegin(GL11.GL_LINE_LOOP);

		for (int i = 0; i < 360; i++) {
			double degInRad = Math.toRadians(i);
			GL11.glVertex2d(Math.cos(degInRad) * radius, Math.sin(degInRad)
					* radius);
		}

		GL11.glEnd();
	}
}
