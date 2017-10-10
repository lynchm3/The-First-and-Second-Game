package com.marklynch.ui;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class ActivityLogger implements Draggable, Scrollable {

	public transient Vector<ActivityLog> logs = new Vector<ActivityLog>();

	public float totalHeight = 0;
	public final static float width = 300;
	public final static float negativeWidth = -width;
	public final static float leftBorder = 20;
	public final static float rightBorder = 20;
	public final static float textWidth = width - leftBorder - rightBorder;
	public float x = 0;
	public float textOffsetY = 0;
	// public float

	public void drawStaticUI() {

		if (x < negativeWidth)
			return;

		// Log
		QuadUtils.drawQuad(Color.BLACK, x, x + width, 0, Game.windowHeight);

		float heightSoFar = 0;

		// Log text
		// for (int i = logs.size() - 1; i > -1; i--) {

		float drawY;
		float drawX = x + leftBorder;
		for (ActivityLog log : logs) {

			drawY = textOffsetY + heightSoFar;

			if (drawY < -20) {
			} else if (drawY > Game.windowHeight) {
			} else {

				TextUtils.printTextWithImages(drawX, drawY, textWidth, true, log.links, log.contents);
			}
			heightSoFar += log.height;
		}

	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (Game.level.showLog == false)
			return false;

		if (mouseX > x && mouseX < x + width && mouseY > 0 && mouseY < 0 + Game.windowHeight) {
			return true;
		}

		return false;
	}

	public void addActivityLog(ActivityLog activityLog) {
		totalHeight += activityLog.height;
		this.logs.add(activityLog);
		scrollToBottom();
	}

	public void scrollToBottom() {

		textOffsetY = 0;
		textOffsetY = Game.windowHeight - totalHeight;
		if (textOffsetY > 0)
			textOffsetY = 0;

	}

	public void removeLastActivityLog() {
		totalHeight -= logs.lastElement().height;
		logs.remove(logs.lastElement());

	}

	@Override
	public void drag(float dragX, float dragY) {
		this.textOffsetY -= dragY;
		if (textOffsetY >= 0) {
			textOffsetY = 0;
		} else if (textOffsetY < Game.windowHeight - totalHeight) {
			textOffsetY = Game.windowHeight - totalHeight;
		}
	}

	@Override
	public void scroll(float dragX, float dragY) {
		drag(dragX, dragY);
	}

}
