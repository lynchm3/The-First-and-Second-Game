package com.marklynch.ui.popups;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

public class Toast {

	public Object[] objects;
	public static float width = 200;
	public float height = 0;
	public float originalHeight = 0;
	public static float halfWidth = width / 2;
	public float y;
	public float textY;
	public static float border = 10f;
	public static float textWidth = width - border * 2;
	public static float x;
	public static float textX;
	public static final float toastTimeMS = 5000f;
	public static final float disapearTime = 1000f;
	public static final float shrinkTime = 100f;
	public float timeRemaingMS = toastTimeMS;
	public Color color = new Color(Color.PINK);
	public Color textColor = new Color(Color.WHITE);
	public float imageAlpha = 1f;

	// Specifics
	public static enum NotificationType {
		QUEST_STARTED, QUEST_UPDATED, QUEST_RESOLVED, LEVEL_UP, MISC, ACTION_DISABLED
	}

	public boolean toast;

	public Toast(Object[] objects) {

		this.objects = objects;
		height = originalHeight = TextUtils.getDimensions(textWidth, objects)[1] + 8;
	}

	public void draw() {

		timeRemaingMS -= Game.delta;
		if (timeRemaingMS <= 0) {
			Level.removeToast(this);
			return;
		}

		if (timeRemaingMS < disapearTime) { /// 1000 to 100
			float alpha = (timeRemaingMS - shrinkTime) / (disapearTime - shrinkTime);
			if (alpha < 0)
				alpha = 0;
			color.setAlpha(alpha);
			textColor.setAlpha(alpha);
			imageAlpha = alpha;
		}

		if (timeRemaingMS < shrinkTime) {
			height = originalHeight * timeRemaingMS / shrinkTime;
		}

		float x = Toast.x;
		float textX = Toast.textX;
		if (toast) {
			x = Toast.x;
			textX = Toast.textX;
		}

		QuadUtils.drawQuad(color, x, y, x + width, y + height);
		TextUtils.printTextWithImages(textX, textY, textWidth, true, null, textColor, imageAlpha, objects);
	}
}
