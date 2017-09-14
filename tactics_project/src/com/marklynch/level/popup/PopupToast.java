package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupToast {

	public Object[] objects;
	public float width;
	public float height;
	public float drawPositionX, drawPositionY;

	public PopupToast(Object[] objects) {
		this.objects = objects;
		drawPositionX = 500;
		drawPositionY = 10;

		if (objects.length == 1)
			this.width = Game.font.getWidth((CharSequence) objects[0]) + 10;
		this.height = 40;
	}

	public void draw() {
		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);
		TextUtils.printTextWithImages(drawPositionX, drawPositionY, width, true, false, objects);
	}
}
