package com.marklynch.level.popup;

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
		drawPositionX = 300;
		drawPositionY = 300;
		this.width = 800;
		this.height = 40;
	}

	public void draw() {
		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);
		TextUtils.printTextWithImages(objects, drawPositionX, drawPositionY, width, true);
	}
}
