package com.marklynch.ui;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

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
		this.texture = ResourceUtils.getGlobalImage(texturePath);
		this.level = level;
	}

	public void draw() {

		TextureUtils.drawTexture(texture, x, x + width, y, y + height);
	}

	public void click() {
		level.endTurn();
	}

}
