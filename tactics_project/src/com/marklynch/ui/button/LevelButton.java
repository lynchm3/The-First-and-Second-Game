package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class LevelButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	Color buttonColor;
	Color textColor;
	Object[] textParts;

	public LevelButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Color buttonColor,
			Color textColor) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.buttonColor = buttonColor;
		this.textColor = textColor;
		this.textParts = new Object[] { new StringWithColor(text, textColor) };
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
				QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(this.textParts, realX, realY, Integer.MAX_VALUE, true);
			} else {
				QuadUtils.drawQuad(buttonColor, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(this.textParts, realX, realY, Integer.MAX_VALUE, true);
			}
		} else {

			QuadUtils.drawQuad(Color.RED, realX, realX + width, realY, realY + height);
			TextUtils.printTextWithImages(this.textParts, realX, realY, Integer.MAX_VALUE, true);
		}

	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2, float boundsY1, float boundsY2) {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (enabled) {
			TextureUtils.drawTextureWithinBounds(enabledTexture, 1.0f, realX, realY, realX + width, realY + height,
					boundsX1, boundsX2, boundsY1, boundsY2);
		} else {
			TextureUtils.drawTextureWithinBounds(disabledTexture, 1.0f, realX, realY, realX + width, realY + height,
					boundsX1, boundsX2, boundsY1, boundsY2);
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

		if (mouseX > realX && mouseX < realX + width && mouseY > realY && mouseY < realY + height) {
			return true;
		}
		return false;
	}

}
