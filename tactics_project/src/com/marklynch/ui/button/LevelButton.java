package com.marklynch.ui.button;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class LevelButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;

	public LevelButton(float x, float y, float width, float height,
			String enabledTexturePath, String disabledTexturePath, String text,
			boolean xFromLeft, boolean yFromTop) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath,
				text);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
	}

	@Override
	public void draw() {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (enabled) {
			if (down) {
				QuadUtils.drawQuad(Color.green, realX, realX + width, realY,
						realY + height);
				TextUtils.printTextWithImages(new Object[] { text }, realX,
						realY);
			} else {
				QuadUtils.drawQuad(Color.blue, realX, realX + width, realY,
						realY + height);
				TextUtils.printTextWithImages(new Object[] { text }, realX,
						realY);
			}
		} else {

			QuadUtils.drawQuad(Color.red, realX, realX + width, realY, realY
					+ height);
			TextUtils.printTextWithImages(new Object[] { text }, realX, realY);
		}

	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2) {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (enabled) {
			TextureUtils.drawTextureWithinBounds(enabledTexture, 1.0f, realX,
					realX + width, realY, realY + height, boundsX1, boundsX2,
					boundsY1, boundsY2);
		} else {
			TextureUtils.drawTextureWithinBounds(disabledTexture, 1.0f, realX,
					realX + width, realY, realY + height, boundsX1, boundsX2,
					boundsY1, boundsY2);
		}

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;
		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (mouseX > realX && mouseX < realX + width && mouseY > realY
				&& mouseY < realY + height) {
			return true;
		}
		return false;
	}

}
