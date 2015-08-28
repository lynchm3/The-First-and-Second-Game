package com.marklynch.ui.button;

import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

public class SelectionWindowButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	public Object object;

	public SelectionWindowButton(float x, float y, float width, float height,
			String enabledTexturePath, String disabledTexturePath, String text,
			boolean xFromLeft, boolean yFromTop, Object object) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath,
				text);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.object = object;
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
				QuadUtils.drawQuad(Color.BLACK, realX, realX + width, realY,
						realY + height);
				TextUtils.printTextWithImages(new Object[] { object }, realX,
						realY, Integer.MAX_VALUE, true);
			} else {
				QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width,
						realY, realY + height);
				TextUtils.printTextWithImages(new Object[] { object }, realX,
						realY, Integer.MAX_VALUE, true);
			}
		} else {

			QuadUtils.drawQuad(Color.RED, realX, realX + width, realY, realY
					+ height);
			TextUtils.printTextWithImages(new Object[] { object }, realX,
					realY, Integer.MAX_VALUE, true);
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

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2) {
	}

}
