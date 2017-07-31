package com.marklynch.ui.button;

import com.marklynch.utils.TextureUtils;

public abstract class ActorButton extends Button {

	public ActorButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, "");
	}

	@Override
	public void draw() {

		if (enabled)
			TextureUtils.drawTexture(enabledTexture, x, y, x + width, y + height);
		else
			TextureUtils.drawTexture(disabledTexture, x, y, x + width, y + height);

	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2, float boundsY1, float boundsY2) {

		if (enabled)
			TextureUtils.drawTextureWithinBounds(enabledTexture, 1.0f, x, y, x + width, y + height, boundsX1, boundsX2,
					boundsY1, boundsY2);
		else
			TextureUtils.drawTextureWithinBounds(disabledTexture, 1.0f, x, y, x + width, y + height, boundsX1, boundsX2,
					boundsY1, boundsY2);

	}

}
