package com.marklynch.ui.button;

import com.marklynch.editor.popup.PopupSelectObject;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	public Object object;
	PopupSelectObject popupSelectObject;

	public PopupButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Object object,
			PopupSelectObject popupSelectObject) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.object = object;
		this.popupSelectObject = popupSelectObject;
	}

	@Override
	public void draw() {

		float realX = x + popupSelectObject.drawPositionX;
		float realY = y + popupSelectObject.drawPositionY;

		if (this.xFromLeft == false)
			realX = popupSelectObject.drawPositionX - x;

		if (this.yFromTop == false)
			realY = popupSelectObject.drawPositionY - y;

		if (enabled) {
			if (down) {
				QuadUtils.drawQuad(Color.BLACK, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(new Object[] { object }, realX, realY, Integer.MAX_VALUE, true);
			} else {
				QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(new Object[] { object }, realX, realY, Integer.MAX_VALUE, true);
			}
		} else {

			QuadUtils.drawQuad(Color.RED, realX, realX + width, realY, realY + height);
			TextUtils.printTextWithImages(new Object[] { object }, realX, realY, Integer.MAX_VALUE, true);
		}

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {

		float realX = x + popupSelectObject.drawPositionX;
		float realY = y + popupSelectObject.drawPositionY;

		if (this.xFromLeft == false)
			realX = popupSelectObject.drawPositionX - x;

		if (this.yFromTop == false)
			realY = popupSelectObject.drawPositionY - y;

		if (mouseX > realX && mouseX < realX + width && mouseY > realY && mouseY < realY + height) {
			return true;
		}
		return false;
	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2, float boundsY1, float boundsY2) {
	}

}
