package com.marklynch.ui.button;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.ResourceUtils;

public abstract class Button {

	public Texture enabledTexture;
	public Texture disabledTexture;
	public float x, y, width, height;
	public Level level;
	public boolean enabled = false;

	public Button(float x, float y, float width, float height,
			String enabledTexturePath, String disabledTexturePath, Level level) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabledTexture = ResourceUtils.getGlobalImage(enabledTexturePath);
		this.disabledTexture = ResourceUtils
				.getGlobalImage(disabledTexturePath);
		this.level = level;
	}

	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {
		if (mouseX > x && mouseX < x + width && mouseY > y
				&& mouseY < y + height) {
			return true;
		}
		return false;
	}

	public abstract void click();

	public abstract void draw();

	public abstract void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2);
	// level.endTurn();

}
