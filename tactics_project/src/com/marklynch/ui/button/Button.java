package com.marklynch.ui.button;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

public abstract class Button {

	public Texture enabledTexture;
	public Texture disabledTexture;
	public int x, y, width, height;
	public Level level;
	public boolean enabled = false;

	public Button(int x, int y, int width, int height,
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

	public abstract void click();
	// level.endTurn();

}
