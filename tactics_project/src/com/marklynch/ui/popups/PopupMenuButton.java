package com.marklynch.ui.popups;

import com.marklynch.ui.button.Button;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

public class PopupMenuButton extends Button {

	boolean drawArrow;
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
			String text, String tooltipText, boolean drawArrow) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text, tooltipText);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.object = object;
		this.popup = popup;
		this.drawArrow = drawArrow;

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
				QuadUtils.drawQuad(colorSelected, realX, realY, realX + width, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, Color.WHITE,
						1f, new Object[] { object });
			} else {
				if (highlighted) {
					QuadUtils.drawQuad(colorHighlighted, realX, realY, realX + width, realY + height);
					TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, Color.WHITE,
							1f, new Object[] { object });
				} else {
					QuadUtils.drawQuad(colorNormal, realX, realY, realX + width, realY + height);
					TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, Color.WHITE,
							1f, new Object[] { object });
				}
			}
		} else {

			QuadUtils.drawQuad(colorDisabled, realX, realY, realX + width, realY + height);
			TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, Color.WHITE,
					1f, new Object[] { object });
		}

		if (drawArrow)
			TextUtils.printTextWithImages(realX + width - 10, realY, Integer.MAX_VALUE, true, null, Color.WHITE,
					1f, new Object[] { ">" });

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

}
