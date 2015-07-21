package com.marklynch.tactics.objects.level;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.utils.TextureUtils;

public class Decoration {

	public transient Texture imageTexture = null;
	public float x, y, width, height;
	boolean background;

	public Decoration(String imagePath) {
		this.imageTexture = getGlobalImage(imagePath);
	}

	public Decoration(float x, float y, float width, float height,
			boolean background, String imagePath) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.background = background;
		this.imageTexture = getGlobalImage(imagePath);
	}

	public void draw() {
		// Draw object
		if (background)
			TextureUtils.drawTexture(imageTexture, x, x + width, y, y + height);
	}

	public void draw2() {
		// Draw object
		if (!background)
			TextureUtils.drawTexture(imageTexture, x, x + width, y, y + height);
	}
}
