package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import com.marklynch.utils.Color;

public class SelectionWindowButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	public Object object;

	public SelectionWindowButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Object object) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text);
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
				QuadUtils.drawQuad(Color.BLACK, realX, realY, realX + width, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, new Object[] { object });
			} else {
				QuadUtils.drawQuad(Color.DARK_GRAY, realX, realY, realX + width, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, new Object[] { object });
			}
		} else {

			QuadUtils.drawQuad(Color.RED, realX, realY, realX + width, realY + height);
			TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, new Object[] { object });
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
