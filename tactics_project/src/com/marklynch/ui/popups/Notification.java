package com.marklynch.ui.popups;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Utils;

public class Notification {

	public Object[] objects;
	public static float width = 200;
	public float height = 0;
	public static float halfWidth = width / 2;
	public static float x;
	public float y;
	public float textY;
	public static float textX;
	public static float border = 20f;
	public static float textWidth = width - border * 2;
	public static float closeButtonX;
	public static float closeButtonWidth = 20f;
	public static float closeButtonHeight = 20f;
	public float closeButtonY;
	public LevelButton closeButton;
	public Object[] turn = new Object[1];

	public boolean flash = false;
	public int flashCounter = 0;

	public ArrayList<Link> links;

	// Specifics
	public static enum NotificationType {
		QUEST_STARTED, QUEST_UPDATED, QUEST_RESOLVED, LEVEL_UP, MISC, ACTION_DISABLED
	}

	NotificationType type;
	Object target;

	public Notification(Object[] objects, NotificationType type, Object target) {

		Utils.printStackTrace();

		this.objects = objects;
		this.type = type;
		this.target = target;
		this.turn[0] = "Turn " + Level.turn;
		closeButton = new LevelButton(0, 0, closeButtonWidth, closeButtonHeight, "end_turn_button.png",
				"end_turn_button.png", "X", true, true, Color.BLACK, Color.WHITE, "Close notification");
		closeButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.notifications.remove(Notification.this);
			}
		});
		height = TextUtils.getDimensions(textWidth, objects)[1] + 8;
		links = TextUtils.getLinks(objects);
	}

	public void draw() {
		if (flash) {
			QuadUtils.drawQuad(Color.WHITE, x, y, x + width, y + height);
		} else {
			QuadUtils.drawQuad(Color.PINK, x, y, x + width, y + height);
		}
		TextUtils.printTextWithImages(textX, textY, textWidth, true, links, objects);
		QuadUtils.drawQuad(Color.BLACK, x + 12, y - 16, x + 76, y + 4);
		TextUtils.printTextWithImages(textX, y - 16, 999, false, null, turn);
		closeButton.draw();
	}

	public boolean mouseOverCloseButton(float mouseX, float mouseY) {
		return closeButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	public boolean equals(Notification otherNotification) {

		if (type == otherNotification.type && target == otherNotification.target) {
			return true;
		}
		return false;
	}
}
