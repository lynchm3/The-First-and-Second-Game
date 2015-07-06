package com.marklynch.ui.button;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

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

	public void draw() {

		if (enabled)
			TextureUtils.drawTexture(enabledTexture, x, x + width, y, y
					+ height);
		else
			TextureUtils.drawTexture(disabledTexture, x, x + width, y, y
					+ height);

	}

	public void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2) {

		if (enabled)
			TextureUtils.drawTextureWithinBounds(enabledTexture, 1.0f, x, x
					+ width, y, y + height, boundsX1, boundsX2, boundsY1,
					boundsY2);
		else
			TextureUtils.drawTextureWithinBounds(disabledTexture, 1.0f, x, x
					+ width, y, y + height, boundsX1, boundsX2, boundsY1,
					boundsY2);

	}

	public abstract void click();
	// level.endTurn();

}
