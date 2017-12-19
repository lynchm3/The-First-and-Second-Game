package com.marklynch.ui.popups;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class Notification {

	public Object[] objects;
	public static float width = 200;
	public float height = 0;
	public static float halfWidth = width / 2;
	public static float x;
	public float y;
	public static float textX;
	public static float border = 20f;
	public static float textWidth = width - border * 2;
	public static float closeButtonX;
	public static float closeButtonWidth = 20f;
	public static float closeButtonHeight = 20f;
	public float closeButtonY;
	public LevelButton closeButton;

	public ArrayList<Link> links;

	public Notification(Object[] objects) {
		this.objects = objects;
		closeButton = new LevelButton(0, 0, closeButtonWidth, closeButtonHeight, "end_turn_button.png",
				"end_turn_button.png", "X", true, true, Color.BLACK, Color.WHITE, "Close notification");
		closeButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.notifications.remove(Notification.this);
			}
		});
		height = TextUtils.getDimensions(textWidth, objects)[1];
		links = TextUtils.getLinks(objects);
	}

	public void draw() {
		QuadUtils.drawQuad(Color.PINK, x, y, x + width, y + height);
		TextUtils.printTextWithImages(textX, y, textWidth, true, links, objects);
		closeButton.draw();
	}

	public boolean mouseOverCloseButton(float mouseX, float mouseY) {
		return closeButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}
}
