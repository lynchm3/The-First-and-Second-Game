package com.marklynch.ui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.Resources;

public class Button {

	public Texture texture;
	public int x, y, width, height;
	public Level level;

	public Button(int x, int y, int width, int height, String texturePath,
			Level level) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = Resources.getGlobalImage(texturePath);
		this.level = level;
	}

	public void draw() {

		// End turn button
		this.texture.bind();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + width, y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + width, y + height);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}

	public void click() {
		level.endTurn();
	}

}
