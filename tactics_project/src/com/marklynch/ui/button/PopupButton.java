package com.marklynch.ui.button;

import com.marklynch.editor.popup.Popup;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	public Object object;
	public Popup popup;

	public PopupButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Object object, Popup popup) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.object = object;
		this.popup = popup;
	}

	@Override
	public void draw() {

		float realX = x + popup.drawPositionX;
		float realY = y + popup.drawPositionY;

		if (this.xFromLeft == false)
			realX = popup.drawPositionX - x;

		if (this.yFromTop == false)
			realY = popup.drawPositionY - y;

		if (enabled) {
			if (down) {
				QuadUtils.drawQuad(Color.BLACK, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, false, null, new Object[] { object });
			} else {
				QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, false, null, new Object[] { object });
			}
		} else {

			QuadUtils.drawQuad(Color.RED, realX, realX + width, realY, realY + height);
			TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, false, null, new Object[] { object });
		}

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {

		float realX = x + popup.drawPositionX;
		float realY = y + popup.drawPositionY;

		if (this.xFromLeft == false)
			realX = popup.drawPositionX - x;

		if (this.yFromTop == false)
			realY = popup.drawPositionY - y;

		if (mouseX > realX && mouseX < realX + width && mouseY > realY && mouseY < realY + height) {
			return true;
		}
		return false;
	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2, float boundsY1, float boundsY2) {
	}

}
