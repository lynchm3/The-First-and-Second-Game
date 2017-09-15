package com.marklynch.ui.popups;

import com.marklynch.ui.button.Button;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupMenuButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	public Object object;
	public PopupMenu popup;

	Color colorNormal = new Color(0f, 0f, 0f, 0.60f);
	Color colorDisabled = new Color(1f, 0.8f, 0.8f, 0.60f);
	Color colorHighlighted = new Color(0f, 0f, 1f, 0.60f);
	Color colorSelected = new Color(0f, 1f, 0f, 0.60f);

	public PopupMenuButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, boolean xFromLeft, boolean yFromTop, Object object, PopupMenu popup,
			String text) {
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
				QuadUtils.drawQuad(colorSelected, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, false, null, new Object[] { object });
			} else {
				if (highlighted) {
					QuadUtils.drawQuad(colorHighlighted, realX, realX + width, realY, realY + height);
					TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, false, null, new Object[] { object });
				} else {
					QuadUtils.drawQuad(colorNormal, realX, realX + width, realY, realY + height);
					TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, false, null, new Object[] { object });
				}
			}
		} else {

			QuadUtils.drawQuad(colorDisabled, realX, realX + width, realY, realY + height);
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
