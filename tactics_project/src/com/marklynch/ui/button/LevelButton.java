package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class LevelButton extends Button {

	public boolean xFromLeft;
	public boolean yFromTop;
	public Color buttonColor;
	private Color textColor;
	public Object[] textParts;
	public Tooltip tooltip;
	public float realX = x;
	public float realY = y;

	public LevelButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Color buttonColor,
			Color textColor, String tooltipText) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.buttonColor = buttonColor;
		this.textColor = textColor;
		this.textParts = new Object[] { new StringWithColor(text, textColor) };

		if (tooltipText != null)
			this.tooltip = new Tooltip(tooltipText, this);

		realX = x;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		realY = y;
		if (this.yFromTop == false)
			realY = Game.windowHeight - y;
	}

	@Override
	public void draw() {
		realX = x;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		realY = y;
		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (enabled) {
			if (down) {
				QuadUtils.drawQuad(Color.DARK_GRAY, realX, realY, realX + width, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, this.textParts);
			} else {
				QuadUtils.drawQuad(buttonColor, realX, realY, realX + width, realY + height);
				TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, this.textParts);
			}
		} else {

			QuadUtils.drawQuad(Color.RED, realX, realY, realX + width, realY + height);
			TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null, this.textParts);
		}

	}

	@Override
	public void drawTooltip() {
		if (tooltip != null)
			tooltip.drawStaticUI();
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

	public void setTextColor(Color color) {
		this.textColor = color;
		this.textParts = new Object[] { new StringWithColor(text.toString(), textColor) };
	}

	public void updatePosition(float x, float y) {

		this.x = x;
		this.y = y;

		realX = x;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		realY = y;
		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

	}

}
