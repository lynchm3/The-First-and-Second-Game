package com.marklynch.ui.popups;

import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

public class Toast {

	public Object[] objects;
	public static float width = 200;
	public float height = 0;
	public static float halfWidth = width / 2;
	public float y;
	public float textY;
	public static float border = 20f;
	public static float textWidth = width - border * 2;
	public static float x;
	public static float textX;

	// Specifics
	public static enum NotificationType {
		QUEST_STARTED, QUEST_UPDATED, QUEST_RESOLVED, LEVEL_UP, MISC, ACTION_DISABLED
	}

	NotificationType type;
	Object target;
	public boolean toast;

	public Toast(Object[] objects, Object target) {

		this.objects = objects;
		this.type = type;
		this.target = target;
		height = TextUtils.getDimensions(textWidth, objects)[1] + 8;
	}

	public void draw() {

		float x = Toast.x;
		float textX = Toast.textX;
		if (toast) {
			x = Toast.x;
			textX = Toast.textX;
		}

		QuadUtils.drawQuad(Color.PINK, x, y, x + width, y + height);
		TextUtils.printTextWithImages(textX, textY, textWidth, true, null, Color.WHITE, objects);
	}
}
