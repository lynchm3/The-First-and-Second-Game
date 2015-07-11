package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.TextureUtils;

public abstract class LevelButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;

	public LevelButton(float x, float y, float width, float height,
			String enabledTexturePath, String disabledTexturePath, Level level,
			boolean xFromLeft, boolean yFromTop) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath,
				level);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
	}

	@Override
	public abstract void click();

	@Override
	public void draw() {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (enabled)
			TextureUtils.drawTexture(enabledTexture, realX, realX + width,
					realY, realY + height);
		else
			TextureUtils.drawTexture(disabledTexture, realX, realX + width,
					realY, realY + height);

	}

	public void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2) {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (enabled)
			TextureUtils.drawTextureWithinBounds(enabledTexture, 1.0f, realX,
					realX + width, realY, realY + height, boundsX1, boundsX2,
					boundsY1, boundsY2);
		else
			TextureUtils.drawTextureWithinBounds(disabledTexture, 1.0f, realX,
					realX + width, realY, realY + height, boundsX1, boundsX2,
					boundsY1, boundsY2);

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;
		if (this.yFromTop == false)
			realY = Game.windowHeight - y;
		
		if (mouseX > realX
				&& mouseX < realX + width
				&& mouseY > realY
				&& mouseY < realY + height) {
			return true;
		}
		return false;
	}

}
