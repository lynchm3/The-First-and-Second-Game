package com.marklynch.ui.button;

import com.marklynch.utils.TextureUtils;

public abstract class ActorButton extends Button {

	public ActorButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, "", null);
	}

	@Override
	public void draw() {

		if (enabled)
			TextureUtils.drawTexture(enabledTexture, x, y, x + width, y + height);
		else
			TextureUtils.drawTexture(disabledTexture, x, y, x + width, y + height);

	}

}
